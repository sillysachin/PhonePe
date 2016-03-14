package me.saket.phonepesaket.utils;

import android.util.Log;

import java.util.Collection;
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

    /**
     * Prints the contents of a Collection.
     */
    public static void log(String tag, Collection collection, String collectionName) {
        if (isEmpty(collection)) {
            Log.w(tag, collectionName + " is empty");
            return;
        }

        Log.d(tag, collectionName + " (" + collection.size() + "): ");
        for (Object object : collection) {
            Log.i(tag, String.valueOf(object));
        }
    }

}