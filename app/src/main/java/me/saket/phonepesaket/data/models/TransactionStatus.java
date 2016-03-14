package me.saket.phonepesaket.data.models;

import com.google.gson.annotations.SerializedName;

/**
 * Status of a transaction made by the user.
 */
public enum TransactionStatus {
    /**
     * User created a transaction and is waiting for the other end to respond.
     */
    @SerializedName("CREATED")
    CREATED,

    /**
     * The transaction was successfully completed. The money has been sent to the receiver.
     */
    @SerializedName("COMPLETED")
    COMPLETED,

    /**
     * The user who created the transaction later canceled it.
     */
    @SerializedName("CANCELLED")
    CANCELLED,

    /**
     * User showed the middle finger. Declined to pay.
     */
    @SerializedName("DECLINED")
    DECLINED,
}