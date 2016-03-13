package me.saket.phonepesaket.data;

/**
 * Primary communication channel of this app.
 * Events can be posted on any thread and are received on the same thread they were emitted on.
 */
public class EventBus {

    private static EventBus sEventBus;
    private org.greenrobot.eventbus.EventBus mInternalBus;

    public EventBus(org.greenrobot.eventbus.EventBus internalBus) {
        mInternalBus = internalBus;
    }

    public static EventBus getInstance() {
        if (sEventBus == null) {
            sEventBus = new EventBus(new org.greenrobot.eventbus.EventBus());
        }
        return sEventBus;
    }

    public void register(Object subscriber) {
        mInternalBus.register(subscriber);
    }

    public void unregister(Object subscriber) {
        mInternalBus.unregister(subscriber);
    }

}