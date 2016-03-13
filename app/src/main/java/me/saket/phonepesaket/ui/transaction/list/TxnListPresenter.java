package me.saket.phonepesaket.ui.transaction.list;

import android.support.annotation.VisibleForTesting;

import com.squareup.otto.Subscribe;

import java.util.List;

import me.saket.phonepesaket.data.TransactionManager;
import me.saket.phonepesaket.data.events.TransactionsUpdateEvent;
import me.saket.phonepesaket.data.models.Transaction;
import me.saket.phonepesaket.ui.BasePresenter;

/**
 * Presenter for {@link TransactionListActivity}
 */
public class TxnListPresenter extends BasePresenter<TxnListContract.View>
        implements TxnListContract.Presenter{

    private TransactionManager mTransactionManager;

    public TxnListPresenter(TxnListContract.View view, TransactionManager transactionManager) {
        super(view);
        mTransactionManager = transactionManager;
    }

    public void onCreate() {
        getView().setupTransactionList();
        refreshUiBasedOnData();
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
     * See {@link TransactionsUpdateEvent}
     */
    @Subscribe
    public void onTransactionsUpdate(TransactionsUpdateEvent updateEvent) {
        refreshUiBasedOnData();
    }

}