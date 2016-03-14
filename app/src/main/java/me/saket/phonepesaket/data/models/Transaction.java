package me.saket.phonepesaket.data.models;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import me.saket.phonepesaket.utils.Objects;

/**
 * Represents one transaction, either pending or completed.
 */
public class Transaction extends RealmObject {

    public static final String COLUMN_TXN_STATUS = "transactionStatus";

    @SerializedName("id")
    @PrimaryKey public long id;

    /**
     * The {@link AccountDetails account} used for making this transaction.
     * This account belongs to the user.
     */
    @SerializedName("accountDetails")
    public AccountDetails accountDetails;

    /**
     * Amount paid / received.
     * See {@link #getAmount()} and {@link #setAmount(BigDecimal)}.
     */
    @SerializedName("amount")
    private String amount;

    /**
     * See {@link #getTransactionType()}
     */
    @SerializedName("type")
    private String transactionType;

    /**
     * See {@link #getTransactionStatus()}
     */
    @SerializedName("status")
    private String transactionStatus;

    /**
     * Phone number of the user at the other side of this transaction.
     */
    @SerializedName("secondUserMobile")
    public String secondUserMobile;

    /**
     * Whether or not this transaction was initiated by this user.
     */
    @SerializedName("isOriginator")
    public boolean isOriginator;

    public Transaction() {
        // Reqd. by Gson
    }

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
        return transactionStatus == null ? null : TransactionStatus.valueOf(transactionStatus);
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = Objects.toStringSafely(transactionStatus);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", accountDetails=" + accountDetails +
                ", amount='" + amount + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", transactionStatus='" + transactionStatus + '\'' +
                '}';
    }

}