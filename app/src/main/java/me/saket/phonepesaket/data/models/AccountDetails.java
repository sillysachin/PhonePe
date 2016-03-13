package me.saket.phonepesaket.data.models;

import java.math.BigDecimal;

import io.realm.RealmObject;

/**
 * Details of a bank account the user can use for making payments.
 */
public class AccountDetails extends RealmObject {

    public long id;

    /**
     * FIXME: Is this correct?
     * Id of the user. Used by the server who has to
     * maintain multiple users unlike this phone.
     */
    public long userId;

    /**
     * Id of the {@link Bank} where this account exists.
     */
    public long bankId;

    public String accountNumber;

    /**
     * Account's IFS code.
     */
    public String ifsc;

    /**
     * Balance the user currently has in this account,
     * updated since we last synced with the server.
     *
     * See {@link #getBalance()} and {@link #setBalance(BigDecimal)}
     */
    private String mBalance;

    /**
     * FIXME
     */
    public boolean isLinked;

    /**
     * FIXME
     */
    public boolean isPrimary;

    /**
     * FIXME
     */
    public boolean isActive;

    public BigDecimal getBalance() {
        return new BigDecimal(mBalance);
    }

    public void setBalance(BigDecimal balance) {
        mBalance = balance.toPlainString();
    }

}