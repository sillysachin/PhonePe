package me.saket.phonepesaket.ui.main;

import me.saket.phonepesaket.data.LocalDataRepository;
import me.saket.phonepesaket.ui.BasePresenter;

/**
 * Presenter for {@link HomeActivity}
 */
public class HomePresenter extends BasePresenter<HomeContract.View>
        implements HomeContract.Presenter {

    public HomePresenter(HomeContract.View view) {
        super(view);
    }

    @Override
    public void onTransactionsClick() {
        getView().showTransactionScreen();
    }

    @Override
    public void onClearAppDataClick() {
        LocalDataRepository.getInstance().deleteAll();
    }

}