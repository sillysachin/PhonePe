package me.saket.phonepesaket.data.models;

import java.math.BigDecimal;

/**
 * Represents one transaction, either pending or completed.
 */
public class Transaction {

    public long id;
    public AccountDetails accountDetails;


    private String amountSerialized;

    public BigDecimal getAmount() {
        return new BigDecimal(amountSerialized);
    }

    public void setAmount(BigDecimal amount) {
        this.amountSerialized = amount.toString();
    }
}