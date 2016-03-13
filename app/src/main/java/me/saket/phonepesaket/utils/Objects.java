package me.saket.phonepesaket.utils;

/**
 * Utility methods related to any objects.
 */
public class Objects {

    public static String toStringSafely(Object object) {
        return object == null ? null : object.toString();
    }

}