package me.saket.phonepesaket.data;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Primary communication channel of this app.
 * Events can be only posted on the main thread.
 */
public class EventBus {

    private static EventBus sEventBus;
    private Bus mInternalBus;

    public EventBus(Bus internalBus) {
        mInternalBus = internalBus;
    }

    public static EventBus getInstance() {
        if (sEventBus == null) {
            sEventBus = new EventBus(new Bus(ThreadEnforcer.MAIN));
        }
        return sEventBus;
    }

    public void register(Object subscriber) {
        mInternalBus.register(subscriber);
    }

    public void unregister(Object subscriber) {
        mInternalBus.unregister(subscriber);
    }

    public void emit(Object event) {
        mInternalBus.post(event);
    }

}