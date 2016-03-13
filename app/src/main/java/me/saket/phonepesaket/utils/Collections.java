package me.saket.phonepesaket.utils;

import android.support.annotation.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Utility methods for Collections (and Arrays too).
 */
public class Collections {

    /**
     * Returns the size of a collection or 0 if it's null.
     */
    public static int size(Collection collection) {
        return collection == null ? 0 : collection.size();
    }

    /**
     * Checks whether or not a Collection is empty (or null).
     */
    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * Checks whether or not a Map is empty (or null).
     */
    public static boolean isEmpty(Map map) {
        return map == null || map.isEmpty();
    }

    /**
     * Checks whether or not an Array is empty (or null).
     */
    public static boolean isEmpty(Object[] objects) {
        return objects == null || objects.length < 1;
    }

    /**
     * Checks whether or not a long array is empty (or null).
     */
    public static boolean isEmpty(long[] array) {
        return array == null || array.length < 1;
    }

    /**
     * Checks whether or not a byte array is empty (or null).
     */
    public static boolean isEmpty(byte[] bytes) {
        return bytes == null || bytes.length <= 0;
    }

    @Nullable
    public static <T> T first(List<T> items) {
        return !isEmpty(items) ? items.get(0) : null;
    }

    /**
     * Returns the last item in a list.
     */
    public static <T> T last(List<T> newResults) {
        return Collections.isEmpty(newResults) ? null : newResults.get(newResults.size() - 1);
    }

}