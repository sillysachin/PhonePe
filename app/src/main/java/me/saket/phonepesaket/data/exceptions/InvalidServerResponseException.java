package me.saket.phonepesaket.data.exceptions;

/**
 * Thrown when the server sends an invalid response that was never expected.
 * Example, the app sends pending / past transactions as null when it's
 * always expected to be a non-null value, even if it's an empty list.
 */
public class InvalidServerResponseException extends Exception {
    
}