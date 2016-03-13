package me.saket.phonepesaket.ui;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Handles:
 * 1. Setting up of support toolbar.
 * 2.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Nullable private BasePresenter<? extends BaseMvpView> mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
    }

    @Nullable
    protected abstract BasePresenter<? extends BaseMvpView> createPresenter();

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        setupToolbar();
    }

    private void setupToolbar() {
        // TODO
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mPresenter != null) {
            mPresenter.registerForEvents();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.unregisterForEvents();
        }
    }

// ======== GETTERS & SETTERS ======== //

    public void setPresenter(@Nullable BasePresenter presenter) {
        mPresenter = presenter;
    }

}