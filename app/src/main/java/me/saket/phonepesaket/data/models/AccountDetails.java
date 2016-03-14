package me.saket.phonepesaket.data.models;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

import io.realm.RealmObject;

/**
 * Details of a bank account the user can use for making payments.
 */
public class AccountDetails extends RealmObject {

    @SerializedName("accountId")
    public long id;

    /**
     * FIXME: Is this correct?
     * User id of the account holder. In this case, this will be the phone user.
     * Probably useful for the server who has to maintain multiple users.
     */
    @SerializedName("userId")
    public long userId;

    /**
     * Id of the {@link Bank} where this account exists.
     */
    @SerializedName("bankId")
    public long bankId;

    /**
     * Details of the {@link Bank} where this account exists.
     */
    @SerializedName("bank")
    public Bank bank;

    /**
     * Account number for this bank account.
     */
    @SerializedName("accountNo")
    public String accountNumber;

    /**
     * Account's IFS code.
     */
    @SerializedName("ifsc")
    public String ifsc;

    /**
     * Balance the user currently has in this account,
     * updated since we last synced with the server.
     *
     * See {@link #getBalance()} and {@link #setBalance(BigDecimal)}
     */
    @SerializedName("balance")
    private String mBalance;

    public boolean isLinked;
    public boolean isPrimary;
    public boolean isActive;

    public AccountDetails() {
        // Reqd. by Gson
    }

    public BigDecimal getBalance() {
        return new BigDecimal(mBalance);
    }

    public void setBalance(BigDecimal balance) {
        mBalance = balance.toPlainString();
    }

    @Override
    public String toString() {
        return "AccountDetails{" +
                "id=" + id +
                ", userId=" + userId +
                ", bank=" + bank +
                ", accountNumber='" + accountNumber + '\'' +
                ", ifsc='" + ifsc + '\'' +
                ", mBalance='" + mBalance + '\'' +
                '}';
    }

}