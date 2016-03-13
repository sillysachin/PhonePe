package me.saket.phonepesaket.data.models;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import me.saket.phonepesaket.utils.Objects;

/**
 * Holds details of a bank that is supported by PhonePe.
 */
public class Bank extends RealmObject {

    @SerializedName("bankId")
    public long bankId;

    /**
     * {@link #getBankName()}
     */
    @SerializedName("bankName")
    private String mBankName;

    /**
     * First 4 characters of this bank's IFSC code since they're constant.
     */
    @SerializedName("ifscPrefix")
    public String ifscPrefix;

    public boolean isPartner;
    public boolean isPremier;
    public int priority;

    /**
     * Name of the bank. See {@link BankName}
     * Eg., HDFC, Citi bank, etc.
     */
    public BankName getBankName() {
        return mBankName == null ? null : BankName.valueOf(mBankName);
    }

    public void setBankName(BankName bankName) {
        mBankName = Objects.toStringSafely(bankName);
    }

}