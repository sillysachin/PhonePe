package me.saket.phonepesaket.data.models;

/**
 * Status of a transaction made by the user.
 */
public enum TransactionStatus {
    /**
     * User created a transaction and is waiting for the other end to respond.
     */
    CREATED,

    /**
     * The transaction was successfully completed. The money has been sent to the receiver.
     */
    COMPLETED,

    /**
     * The user who created the transaction later canceled it.
     */
    CANCELLED,

    /**
     * User showed the middle finger. Declined to pay.
     */
    DECLINED,
}