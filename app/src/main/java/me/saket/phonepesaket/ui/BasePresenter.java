package me.saket.phonepesaket.ui;

import me.saket.phonepesaket.data.EventBus;

/**
 * Base class for all View presenters.
 * Provides
 * 1. Access to the event-bus
 * 2.
 */
public abstract class BasePresenter<V extends BaseMvpView> {

    protected V mView;

    public BasePresenter(V view) {
        mView = view;
    }

    public void registerForEvents() {
        getEventBus().register(this);
    }

    public void unregisterForEvents() {
        getEventBus().unregister(this);
    }

// ======== GETTERS & SETTERS ======== //

    public V getView() {
        return mView;
    }

    protected EventBus getEventBus() {
        return EventBus.getInstance();
    }

}