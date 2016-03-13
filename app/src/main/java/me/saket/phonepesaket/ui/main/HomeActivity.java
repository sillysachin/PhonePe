package me.saket.phonepesaket.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;

import butterknife.ButterKnife;
import butterknife.OnClick;
import me.saket.phonepesaket.R;
import me.saket.phonepesaket.ui.BaseActivity;
import me.saket.phonepesaket.ui.BaseMvpView;
import me.saket.phonepesaket.ui.BasePresenter;
import me.saket.phonepesaket.ui.transaction.list.TransactionListActivity;

/**
 * Home-screen of the app.
 */
public class HomeActivity extends BaseActivity implements HomeContract.View {

    private HomePresenter mHomePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Nullable
    @Override
    protected BasePresenter<? extends BaseMvpView> getPresenter() {
        mHomePresenter = new HomePresenter(this);
        return mHomePresenter;
    }

    @OnClick(R.id.btn_transactions)
    void onTransactionsButtonClick() {
        mHomePresenter.onTransactionsClick();
    }

    @Override
    public void showTransactionScreen() {
        TransactionListActivity.start(this);
    }

}