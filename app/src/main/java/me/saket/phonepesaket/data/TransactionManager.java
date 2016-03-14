package me.saket.phonepesaket.data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import me.saket.phonepesaket.data.events.ApiTimeoutErrorEvent;
import me.saket.phonepesaket.data.events.GenericNetworkErrorEvent;
import me.saket.phonepesaket.data.events.TransactionListDownSyncEndEvent;
import me.saket.phonepesaket.data.events.TransactionListDownSyncStartEvent;
import me.saket.phonepesaket.data.events.TransactionsTableUpdateEvent;
import me.saket.phonepesaket.data.models.Transaction;
import me.saket.phonepesaket.data.models.TransactionStatus;
import me.saket.phonepesaket.data.models.TransactionSyncResponse;
import me.saket.phonepesaket.data.rxfunctions.ExtractRetrofitResponseOrThrowError;
import me.saket.phonepesaket.data.rxfunctions.ExtractTransactionsOrThrowError;
import me.saket.phonepesaket.services.PhonePeApi;
import me.saket.phonepesaket.utils.RxUtils;
import retrofit2.Response;
import rx.Notification;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

import static io.realm.Realm.Transaction.OnError;
import static io.realm.Realm.Transaction.OnSuccess;

/**
 * The data layer for managing all transactions across the app. Provides
 * data from app storage or the server (if a local copy isn't present).
 * Handles syncing with the server too.
 * <p>
 * All communication with this manager should ideally by one-way.
 * (Presenter -> Manager), but it's okay in cases it's unavoidable.
 */
public class TransactionManager {

    private static final String TAG = "TransactionManager";
    public static final int NO_OF_ITEMS_PER_PAGINATION = 10;

    private static TransactionManager sTransactionManager;
    private LocalDataRepository mLocalDataRepository;
    private EventBus mEventBus;
    private DataRepoErrorHandler mDbErrorHandler;
    private PhonePeApi mPhonePeApi;

    public TransactionManager(LocalDataRepository localDataRepository, DataRepoErrorHandler
            dbErrorHandler, EventBus eventBus, PhonePeApi phonePeApi) {
        mLocalDataRepository = localDataRepository;
        mDbErrorHandler = dbErrorHandler;
        mEventBus = eventBus;
        mPhonePeApi = phonePeApi;
    }

    public static TransactionManager getInstance() {
        if (sTransactionManager == null) {
            sTransactionManager = new TransactionManager(
                    LocalDataRepository.getInstance(),
                    DataRepoErrorHandler.getInstance(),
                    EventBus.getInstance(),
                    PhonePeApi.getInstance()
            );
        }
        return sTransactionManager;
    }

// ======== TRANSACTION LIST ======== //

    /**
     * Returns all transactions pending-to-be-made by the user cached in the
     * app's DB. These will be transactions having transaction-status as
     * {@link TransactionStatus#CREATED}
     */
    public List<Transaction> getPendingTransactions() {
        try {
            return mLocalDataRepository.getPendingTransactions();
        } catch (Exception e) {
            mDbErrorHandler.handle(e);
            return null;
        }
    }

    /**
     * Gets all transactions completed by the user cached in the app. These will
     * be transactions having any one of these statuses:
     * <p>
     * {@link TransactionStatus#COMPLETED},
     * {@link TransactionStatus#DECLINED} or
     * {@link TransactionStatus#CANCELLED},
     */
    public List<Transaction> getPastTransactions() {
        try {
            return mLocalDataRepository.getPastTransactions();
        } catch (Exception e) {
            mDbErrorHandler.handle(e);
            return null;
        }
    }

    /**
     * Saves transactions completed by or pending from the user.
     */
    private void savePastTransactions(List<Transaction> transactions) {
        final OnSuccess successCallback = this::emitTransactionsTableUpdateEvent;
        final OnError errorCallback = error -> mDbErrorHandler.handle(error);
        mLocalDataRepository.saveTransactions(transactions, successCallback, errorCallback);
    }

// ======== API CALLS ======== //

    /**
     * The server does not support pagination yet, so I'm mimicking it manually
     * here. I need two values for this:
     * <p>
     * 1. "From": From where do we want the next data to start. If we already
     * have 10 items in the list, this will be 11. In our case, this would
     * be the no. of items already present in local data-store.
     * <p>
     * Ideally, this should be some timestamp.
     * <p>
     * 2. No. of items to load.
     */
    public void loadMorePastTransactionsFromServer() {
        final int fromIndex = getPastTransactions().size();
        final int itemsToLoad = NO_OF_ITEMS_PER_PAGINATION;

        mPhonePeApi.getPastTransactions()
                .doOnNext(emitTransactionSyncStartEvent())
                .map(new ExtractRetrofitResponseOrThrowError<>())
                .map(new ExtractTransactionsOrThrowError<>())
                .map(filterValidPastTransactions())
                .doOnEach(emitTransactionSyncEndEvent())
                .compose(RxUtils.applySchedulers())
                .subscribe(new Subscriber<List<Transaction>>() {
                    @Override
                    public void onError(Throwable e) {
                        emitTransactionSyncEndEvent();
                        handlePastTransactionsSyncError(e);
                    }

                    @Override
                    public void onNext(List<Transaction> transactions) {
                        emitTransactionSyncEndEvent();
                        savePastTransactions(transactions);
                    }

                    @Override
                    public void onCompleted() {

                    }
                });

    }

    /**
     * Handles any errors encountered while down-syncing transactions with the server.
     */
    void handlePastTransactionsSyncError(Throwable e) {
        if (e instanceof TimeoutException) {
            mEventBus.emit(new ApiTimeoutErrorEvent());

        } else {
            // Show generic error to user.
            emitGenericNetworkError();
            e.printStackTrace();
        }
    }

    /**
     * PhonePe's API contains mixed set of transactions. We only want to keep past
     * transactions here. That is, transactions with non-CREATED status. This
     * function does exactly that.
     */
    public Func1<List<Transaction>, List<Transaction>> filterValidPastTransactions() {
        return transactions -> {
            final List<Transaction> actualPastTransactions = new ArrayList<>(transactions.size());
            for (final Transaction transaction : transactions) {
                if (transaction.getTransactionStatus() == TransactionStatus.CREATED) {
                    continue;
                }

                if (transaction.accountDetails == null) {
                    // Should never happen, but the mock API
                    // response contains null bank items.
                    continue;
                }

                actualPastTransactions.add(transaction);
            }
            return actualPastTransactions;
        };
    }

// ======== EVENTS ======== //

    /**
     * Called whenever some Transaction(s) get updated.
     */
    void emitTransactionsTableUpdateEvent() {
        mEventBus.emit(new TransactionsTableUpdateEvent());
    }

    /**
     * Called when transaction-list downSyncing starts.
     */
    private Action1<Response<TransactionSyncResponse>> emitTransactionSyncStartEvent() {
        return response -> mEventBus.emit(new TransactionListDownSyncStartEvent());
    }

    /**
     * Called when transaction-list downSyncing ends.
     */
    private Action1<Notification<? super List<Transaction>>> emitTransactionSyncEndEvent() {
        return notification -> {
            switch (notification.getKind()) {
                case OnNext:
                case OnError:
                    mEventBus.emit(new TransactionListDownSyncEndEvent());
                    break;
            }
        };
    }

    /**
     * Called when the server could not be reached or an invalid response was received.
     */
    private void emitGenericNetworkError() {
        mEventBus.emit(new GenericNetworkErrorEvent());
    }

}