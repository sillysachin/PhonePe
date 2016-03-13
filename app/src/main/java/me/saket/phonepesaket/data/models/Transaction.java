package me.saket.phonepesaket.data.models;

import java.math.BigDecimal;

import io.realm.RealmObject;
import me.saket.phonepesaket.utils.Objects;

/**
 * Represents one transaction, either pending or completed.
 */
public class Transaction extends RealmObject {

    public static final String COLUMN_TXN_STATUS = "transactionStatus";
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
    private String amount;

    /**
     * See {@link #getTransactionType()}
     */
    private String transactionType;

    /**
     * See {@link #getTransactionStatus()}
     */
    private String transactionStatus;

    /**
     * Phone number of the user at the other side of this transaction.
     */
    public String secondUserMobile;

    /**
     * Whether or not this transaction was initiated by this user.
     */
    public boolean isOriginator;

    public BigDecimal getAmount() {
        return new BigDecimal(amount);
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount.toPlainString();
    }

    /**
     * One of
     * {@link TransactionType#COLLECT COLLECT},
     * {@link TransactionType#PAY PAY} or
     * {@link TransactionType#RECHARGE RECHARGE}.
     */
    public TransactionType getTransactionType() {
        return transactionType == null ? null : TransactionType.valueOf(transactionType);
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = Objects.toStringSafely(transactionType);
    }

    /**
     * One of
     * {@link TransactionStatus#CREATED CREATED},
     * {@link TransactionStatus#CANCELLED CANCELLED},
     * {@link TransactionStatus#COMPLETED COMPLETED} or
     * {@link TransactionStatus#DECLINED DECLINED}.
     */
    public TransactionStatus getTransactionStatus() {
        return transactionStatus == null ? null : TransactionStatus.valueOf(transactionType);
    }

    public void setTransactionType(TransactionStatus transactionStatus) {
        this.transactionStatus = Objects.toStringSafely(transactionStatus);
    }

}