package me.saket.phonepesaket.data;

import android.support.annotation.NonNull;

import java.util.List;

import io.realm.Realm;
import me.saket.phonepesaket.data.models.Transaction;
import me.saket.phonepesaket.data.models.TransactionStatus;

/**
 * Handles storing and retrieving app's local data.
 * Should only be accessed through managers, like {@link TransactionManager}.
 */
public class LocalDataRepository {

    public static final long DATABASE_VERSION = 0L;
    public static final String DATABASE_NAME = "PhonePeDatabase";

    private static LocalDataRepository sLocalDataRepository;
    private Realm mRealm;

    public LocalDataRepository(Realm realm) {
        mRealm = realm;
    }

    public static LocalDataRepository getInstance() {
        if (sLocalDataRepository == null) {
            sLocalDataRepository = new LocalDataRepository(Realm.getDefaultInstance());
        }
        return sLocalDataRepository;
    }

    /**
     * Gets all transactions with status as {@link TransactionStatus#CREATED}.
     * Returns an empty list when there are no pending transactions.
     */
    @NonNull
    public List<Transaction> getPendingTransactions() {
        return mRealm.where(Transaction.class)
                .equalTo(Transaction.COLUMN_TXN_STATUS, TransactionStatus.CREATED.name())
                .findAll();
    }

    /**
     * Gets all transactions with any status OTHER THAN {@link TransactionStatus#CREATED}.
     * Returns an empty list when there are no past transactions.
     */
    @NonNull
    public List<Transaction> getPastTransactions() {
        return mRealm.where(Transaction.class)
                .notEqualTo(Transaction.COLUMN_TXN_STATUS, TransactionStatus.CREATED.name())
                .findAll();
    }

}