package me.saket.phonepesaket.data.models;

import com.google.gson.annotations.SerializedName;

/**
 * Type of a transaction the user made.
 */
public enum TransactionType {
    /**
     * User initiated this transaction on his own.
     */
    @SerializedName("PAY")
    PAY,

    /**
     * Some other user requested a payment. This could a service or some person.
     * Eg., when buying something in BigBazaar, they will collect payment from the user.
     */
    @SerializedName("COLLECT")
    COLLECT,

    /**
     * Phone recharge
     */
    @SerializedName("RECHARGE")
    RECHARGE,
}