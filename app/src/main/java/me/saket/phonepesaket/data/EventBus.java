package me.saket.phonepesaket.data;

import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Primary communication channel of this app.
 * Events can be only posted on any thread, but they'll always be recevied on the main thread.
 */
public class EventBus {

    private static EventBus sEventBus;
    private Bus mInternalBus;
    private Handler mUiThreadLooper = new Handler(Looper.getMainLooper());

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

    /**
     * Events always get posted on the main thread — regardless
     * of the thread this method was called from.
     */
    public void emit(Object event) {
        // Proxy through the main thread's looper this
        // method was called from a background thread.
        if (Looper.getMainLooper() != Looper.myLooper()) {
            mUiThreadLooper.post(() -> {
                mInternalBus.post(event);
            });
        } else {
            mInternalBus.post(event);
        }
    }

}