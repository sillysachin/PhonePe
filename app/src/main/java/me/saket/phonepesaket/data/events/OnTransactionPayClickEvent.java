package me.saket.phonepesaket.data.events;

import me.saket.phonepesaket.data.models.Transaction;

/**
 * Emitted when the user wants to pay for a transaction.
 */
public class OnTransactionPayClickEvent {

    public Transaction transactionToPay;

    /**
     * @param transactionToPay The transaction the user wants to pay.
     */
    public OnTransactionPayClickEvent(Transaction transactionToPay) {
        this.transactionToPay = transactionToPay;
    }

}