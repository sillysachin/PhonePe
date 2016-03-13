package me.saket.phonepesaket.data.models;

/**
 * Status of a transaction made by the user.
 */
public enum TransactionStatus {
    /**
     * FIXME
     */
    CREATED,

    /**
     * The transaction was successfully completed. The money has been sent to the receiver.
     */
    COMPLETED,

    /**
     * FIXME
     */
    CANCELLED,

    /**
     * User showed the middle finger. Declined to pay.
     */
    DECLINED,
}