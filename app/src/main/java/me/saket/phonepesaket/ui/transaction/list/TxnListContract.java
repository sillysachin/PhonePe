package me.saket.phonepesaket.ui.transaction.list;

import java.util.List;

import me.saket.phonepesaket.data.models.Transaction;
import me.saket.phonepesaket.ui.BaseMvpView;

public interface TxnListContract {

    interface View extends BaseMvpView {
        /**
         * Called on activity start
         */
        void setupTransactionList();

        /**
         * Called everytime the data-set has to be refreshed or down-syncing of more
         * items starts.
         */
        void updateTransactionList(List<Transaction> pendingTransactions, List<Transaction>
                pastTransactions);

        /**
         * Called before {@link #updateTransactionList(List, List)} to show or
         * hide the progress indicator that more items are being fetched from
         * the server.
         */
        void setListLoadingProgressIndicatorVisible(boolean visible);

        /**
         * Called when some error occurred while communicating to the server.
         */
        void showGenericNetworkError();

        /**
         * Called when there are no more items on the server
         */
        void disableLoadMoreTransactionsButton();
    }

    interface Presenter {

        void onLoadMoreTransactionsClick();
    }

}