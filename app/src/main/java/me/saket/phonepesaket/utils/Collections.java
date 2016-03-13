package me.saket.phonepesaket.utils;

import android.support.annotation.Nullable;
import android.support.v4.util.SparseArrayCompat;
import android.util.Log;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Utility methods for Collections (and Arrays too).
 */
public class Collections {

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
     * Checks whether or not a SparseArrayCompat is empty (or null).
     */
    private static boolean isEmpty(SparseArrayCompat sparseArray) {
        return sparseArray == null || sparseArray.size() < 1;
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
    public static void dump(String tag, Collection collection, String collectionName) {
        if (isEmpty(collection)) {
            Log.w(tag, collectionName + " is empty");
            return;
        }

        Log.d(tag, collectionName + " (" + collection.size() + "): ");
        for (Object object : collection) {
            Log.i(tag, String.valueOf(object));
        }
    }

    /**
     * Prints the contents of an array.
     */
    public static void dump(String tag, Object[] objects, String collectionName) {

        if (objects == null) {
            Log.w(tag, collectionName + " is empty");
            return;
        }

        dump(tag, Arrays.asList(objects), collectionName);

    }

    /**
     * Prints the contents of a Map.
     */
    public static <K, V> void dump(String tag, Map<K, V> map, String mapName) {
        if (isEmpty(map)) {
            Log.w(tag, mapName + " is empty");
            return;
        }

        Log.d(tag, "Items in " + mapName + " (" + map.size() + "): ");
        for (Map.Entry<K, V> entry : map.entrySet())
            Log.i(tag, entry.getKey() + " -> " + entry.getValue());

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