package me.saket.phonepesaket.ui;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.ButterKnife;
import me.saket.phonepesaket.R;
import me.saket.phonepesaket.data.EventBus;

/**
 * Handles:
 * 1. Setting up of support toolbar.
 * 2. Access to the event bus.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        setupToolbar();
    }

    private void setupToolbar() {
        final Toolbar toolbar = ButterKnife.findById(this, R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            //noinspection ConstantConditions
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (getPresenter() != null) {
            getPresenter().registerForEvents();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (getPresenter() != null) {
            getPresenter().unregisterForEvents();
        }
    }

    @Nullable
    protected abstract BasePresenter<? extends BaseMvpView> getPresenter();

    protected EventBus getEventBus() {
        return EventBus.getInstance();
    }

}