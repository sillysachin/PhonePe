package me.saket.phonepesaket.data.models;

import com.google.gson.annotations.SerializedName;

/**
 * Banks supported by PhonePe.
 */
public enum BankName {
    @SerializedName("HDFC")
    HDFC,

    @SerializedName("CITI BANK")
    CITI_BANK,

    @SerializedName("AXIS BANK")
    AXIS_BANK,

    @SerializedName("ICICI")
    ICICI,
}