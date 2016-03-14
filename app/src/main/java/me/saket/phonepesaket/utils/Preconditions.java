package me.saket.phonepesaket.utils;

import java.util.Collection;

/**
 * Use these methods to "fail fast" if method parameters are received as null (or empty).
 */
public class Preconditions {

    public static <T> T checkNotNull(T item) {
        if (item == null) {
            throw new NullPointerException();
        }
        return item;
    }

    public static Collection checkNotEmpty(Collection collection, String collectionName) {
        if (Collections.isEmpty(collection)) {
            if (collectionName != null) {
                throw new NullPointerException(collectionName + " cannot be empty");
            } else {
                throw new NullPointerException();
            }
        }
        return collection;
    }

    public static Collection checkNotEmpty(Collection collection) {
        return checkNotEmpty(collection, null);
    }

    public static <T> T[] checkNotEmpty(T[] array, String collectionName) {
        if (Collections.isEmpty(array)) {
            throw new NullPointerException(collectionName + " cannot be empty");
        }
        return array;
    }

    public static <T> T[] checkNotEmpty(T[] array) {
        return checkNotEmpty(array, "array");
    }
}