package me.saket.phonepesaket.data;

import java.util.List;

import me.saket.phonepesaket.data.models.Transaction;
import me.saket.phonepesaket.data.models.TransactionStatus;

/**
 * The data layer for managing all transactions across the app.
 * Provides data from app storage or the server (if a local copy isn't present).
 * Handles syncing with the server too.
 */
public class TransactionManager {

    private static TransactionManager sTransactionManager;
    private LocalDataRepository mLocalDataRepository;
    private EventBus mEventBus;
    private DataRepoErrorHandler mErrorHandler;

    public TransactionManager(LocalDataRepository localDataRepository, DataRepoErrorHandler errorHandler,
                              EventBus eventBus) {
        mLocalDataRepository = localDataRepository;
        mErrorHandler = errorHandler;
        mEventBus = eventBus;
    }

    public static TransactionManager getInstance() {
        if (sTransactionManager == null) {
            sTransactionManager = new TransactionManager(
                    LocalDataRepository.getInstance(),
                    DataRepoErrorHandler.getInstance(),
                    EventBus.getInstance()
            );
        }
        return sTransactionManager;
    }

// ======== TRANSACTION LIST ======== //

    /**
     * Returns all transactions pending-to-be-made by the user. These will be
     * transactions having transaction-status as {@link TransactionStatus#CREATED}
     */
    public List<Transaction> getPendingTransactions() {
        try {
            return mLocalDataRepository.getPendingTransactions();
        } catch (Exception e) {
            mErrorHandler.handle(e);
            return null;
        }
    }

    /**
     * Gets all transactions completed by the user. These will be transactions
     * having any of these statuses:
     * {@link TransactionStatus#COMPLETED},
     * {@link TransactionStatus#DECLINED} or
     * {@link TransactionStatus#CANCELLED},
     */
    public List<Transaction> getPastTransactions() {
        try {
            return mLocalDataRepository.getPastTransactions();
        } catch (Exception e) {
            mErrorHandler.handle(e);
            return null;
        }
    }

}