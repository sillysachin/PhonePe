package me.saket.phonepesaket.ui;

import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import me.saket.phonepesaket.data.EventBus;

/**
 * Handles:
 * 1. Setting up of support toolbar.
 * 2. Subscribing and unSubscribing to the event-bus. This way, all activities that extend
 *    this class get easy access to it.
 */
public abstract class BaseActivity extends AppCompatActivity {

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
        getEventBus().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getEventBus().unregister(this);
    }

// ======== GETTERS ======== //

    protected EventBus getEventBus() {
        return EventBus.getInstance();
    }

}