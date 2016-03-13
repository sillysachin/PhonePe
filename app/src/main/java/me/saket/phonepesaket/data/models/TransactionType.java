package me.saket.phonepesaket.data.models;

/**
 * Type of a transaction the user made.
 */
public enum TransactionType {
    /**
     * FIXME
     * User initiated transaction?
     */
    PAY,

    /**
     * FIXME
     * Payment made to a service, for example a store.
     * The user was asked to make this payment.
     */
    COLLECT,

    /**
     * Phone recharge
     */
    RECHARGE,
}