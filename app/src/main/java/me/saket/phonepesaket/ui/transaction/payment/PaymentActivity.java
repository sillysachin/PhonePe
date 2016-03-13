package me.saket.phonepesaket.ui.transaction.payment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import me.saket.phonepesaket.R;
import me.saket.phonepesaket.ui.BaseActivity;
import me.saket.phonepesaket.ui.BaseMvpView;
import me.saket.phonepesaket.ui.BasePresenter;

public class PaymentActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
    }

    @Nullable
    @Override
    protected BasePresenter<? extends BaseMvpView> createPresenter() {
        return null;
    }

}