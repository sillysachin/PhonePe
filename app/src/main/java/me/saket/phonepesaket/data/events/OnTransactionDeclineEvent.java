package me.saket.phonepesaket.data.events;

import me.saket.phonepesaket.data.models.Transaction;

/**
 * Emitted when the user declines to pay for a transaction.
 * This should never be for a transaction the user created himself / herself.
 */
public class OnTransactionDeclineEvent {

    public Transaction declinedTransaction;

    /**
     * @param declinedTransaction The transaction the user does not want to pay.
     */
    public OnTransactionDeclineEvent(Transaction declinedTransaction) {
        this.declinedTransaction = declinedTransaction;
    }

}