package me.saket.phonepesaket.data.models;

import io.realm.RealmObject;
import me.saket.phonepesaket.utils.Objects;

/**
 * Holds details of a bank that is supported by PhonePe.
 */
public class Bank extends RealmObject {

    public long bankId;

    /**
     * Name of the bank.
     * Eg., HDFC, Citi bank, etc.
     */
    private String mBankName;

    /**
     * First 4 characters of this bank's IFSC code since they're constant.
     */
    public String ifscPrefix;

    /**
     * FIXME
     */
    public boolean isPartner;

    /**
     * FIXME
     */
    public boolean isPremier;

    /**
     * FIXME
     */
    public int priority;

    public BankName getBankName() {
        return mBankName == null ? null : BankName.valueOf(mBankName);
    }

    public void setBankName(BankName bankName) {
        mBankName = Objects.toStringSafely(bankName);
    }

}