package me.saket.phonepesaket.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;

import me.saket.phonepesaket.R;
import me.saket.phonepesaket.ui.BaseActivity;
import me.saket.phonepesaket.ui.BaseMvpView;
import me.saket.phonepesaket.ui.BasePresenter;

/**
 * Home-screen of the app.
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Nullable
    @Override
    protected BasePresenter<? extends BaseMvpView> createPresenter() {
        return null;
    }

}