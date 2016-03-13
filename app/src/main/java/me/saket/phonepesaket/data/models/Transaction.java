package me.saket.phonepesaket.data.models;

import java.math.BigDecimal;

import io.realm.RealmObject;
import me.saket.phonepesaket.utils.Objects;

/**
 * Represents one transaction, either pending or completed.
 */
public class Transaction extends RealmObject {

    public long id;

    /**
     * The {@link AccountDetails account} used for making this transaction.
     * This account belongs to the user.
     */
    public AccountDetails accountDetails;

    /**
     * Amount paid / received.
     * See {@link #getAmount()} and {@link #setAmount(BigDecimal)}.
     */
    private String mAmount;

    /**
     * One of
     * {@link TransactionType#COLLECT COLLECT},
     * {@link TransactionType#PAY PAY} or
     * {@link TransactionType#RECHARGE RECHARGE}.
     */
    private String mTransactionType;

    /**
     * FIXME
     * Phone number of the user at the other side of this transaction.
     */
    public String secondUserMobile;

    /**
     * FIXME
     * Whether or not this transaction was initiated by this user.
     */
    public boolean isOriginator;

    public BigDecimal getAmount() {
        return new BigDecimal(mAmount);
    }

    public void setAmount(BigDecimal amount) {
        this.mAmount = amount.toPlainString();
    }

    public TransactionType getTransactionType() {
        return mTransactionType == null ? null : TransactionType.valueOf(mTransactionType);
    }

    public void setTransactionType(TransactionType transactionType) {
        mTransactionType = Objects.toStringSafely(transactionType);
    }

}