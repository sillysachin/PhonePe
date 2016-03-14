package me.saket.phonepesaket.utils;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Utility methods to make working with RxAndroid easier.
 */
public class RxUtils {

    /**
     * Simple transformer that will take an observable and
     * 1. Schedule it on a worker thread
     * 2. Observe on main thread
     */
    public static <T> Observable.Transformer<T, T> applySchedulers() {
        return observable -> {
            return observable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        };
    }

}