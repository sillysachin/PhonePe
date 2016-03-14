package me.saket.phonepesaket.ui.transaction.list;

import java.util.List;

import me.saket.phonepesaket.data.models.Transaction;
import me.saket.phonepesaket.ui.BaseMvpView;

public interface TxnListContract {

    interface View extends BaseMvpView {

        void setupTransactionList();

        void updateTransactionList(List<Transaction> pendingTransactions, List<Transaction>
                pastTransactions);
    }

    interface Presenter {

        void onLoadMoreTransactionsClick();
    }

}