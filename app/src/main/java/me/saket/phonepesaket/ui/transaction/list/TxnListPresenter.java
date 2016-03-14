package me.saket.phonepesaket.ui.transaction.list;

import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.squareup.otto.Subscribe;

import java.util.List;

import me.saket.phonepesaket.data.TransactionManager;
import me.saket.phonepesaket.data.events.GenericNetworkErrorEvent;
import me.saket.phonepesaket.data.events.PastTransactionsPaginationEndReachedEvent;
import me.saket.phonepesaket.data.events.TransactionListDownSyncEndEvent;
import me.saket.phonepesaket.data.events.TransactionListDownSyncStartEvent;
import me.saket.phonepesaket.data.events.TransactionsTableUpdateEvent;
import me.saket.phonepesaket.data.models.Transaction;
import me.saket.phonepesaket.ui.BasePresenter;

/**
 * Presenter for {@link TransactionListActivity}
 */
public class TxnListPresenter extends BasePresenter<TxnListContract.View>
        implements TxnListContract.Presenter{

    private static final String TAG = "TxnListPresenter";
    private TransactionManager mTransactionManager;

    public TxnListPresenter(TxnListContract.View view, TransactionManager transactionManager) {
        super(view);
        mTransactionManager = transactionManager;
    }

    public void onCreate() {
        // Setup the initial layout
        getView().setupTransactionList();

        // Get transactions that are present locally
        refreshUiBasedOnData();

        // Trigger a sync so that new changes in the app as well as on the server
        // get synced with the app. The manager handles saving the received
        // transactions and emitting an event when it's done. See onTransactionsUpdate().
        mTransactionManager.loadMorePastTransactionsFromServer();
        mTransactionManager.loadMorePendingTransactionsFromServer();
    }

    /**
     * Re-queries the available transactions from {@link TransactionManager}
     * and updates the transaction list.
     */
    @VisibleForTesting
    void refreshUiBasedOnData() {
        final List<Transaction> pendingTransactions = mTransactionManager.getPendingTransactions();
        final List<Transaction> pastTransactions = mTransactionManager.getPastTransactions();
        mView.updateTransactionList(pendingTransactions, pastTransactions);
    }

    /**
     * See {@link TransactionsTableUpdateEvent}
     */
    @Subscribe
    public void onTransactionsUpdate(TransactionsTableUpdateEvent updateEvent) {
        refreshUiBasedOnData();
    }

    @Subscribe
    public void onNetworkError(GenericNetworkErrorEvent networkErrorEvent) {
        mView.showGenericNetworkError();
    }

// ======== PAGINATION ======== //

    @Override
    public void onLoadMoreTransactionsClick() {
        mTransactionManager.loadMorePastTransactionsFromServer();
    }

    @Subscribe
    public void onLoadMoreTransactionsStart(TransactionListDownSyncStartEvent startEvent) {
        // Show progress circle
        Log.i(TAG, "Downsyncing start");
        mView.setListLoadingProgressIndicatorVisible(true);
        refreshUiBasedOnData();
    }

    @Subscribe
    public void onLoadMoreTransactionsComplete(TransactionListDownSyncEndEvent endEvent) {
        // Hide progress circle in the list only if no transactions
        // of any type are being down-synced
        Log.i(TAG, "Downsyncing end");
        mView.setListLoadingProgressIndicatorVisible(false);
        refreshUiBasedOnData();
    }

    @Subscribe
    public void onPaginationEndReached(PastTransactionsPaginationEndReachedEvent endReachedEvent) {
        mView.disableLoadMoreTransactionsButton();
    }

}