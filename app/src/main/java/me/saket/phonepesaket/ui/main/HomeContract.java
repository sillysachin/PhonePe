package me.saket.phonepesaket.ui.main;

import me.saket.phonepesaket.ui.BaseMvpView;

public interface HomeContract {

    interface View extends BaseMvpView {
        void showTransactionScreen();
    }

    interface Presenter {
        void onTransactionsClick();

        void onClearAppDataClick();
    }

}