package me.saket.phonepesaket.data;

/**
 * The data layer for managing all transactions across the app.
 * Provides data from app storage or the server (if a local copy isn't present).
 * Handles syncing with the server too.
 */
public class TransactionManager {



// ======== TRANSACTION LIST ======== //

    /**
     * Gets all transactions pending-to-be-made by the user.
     */
    public void getPending() {
        // TODO
    }

    /**
     * Gets all transactions made in the past.
     */
    public void getHistory() {
        // TODO
    }

    /**
     * Gets the details of one transaction.
     */
    public void get(long transactionId) {
        // TODO
    }

    /**
     * Gets all transactions made with a specific user.
     */
    public void getWithUser(long userId) {
        // TODO
    }

// ======== PAYMENT ======== //

    public void completeTransaction() {
        // TODO
    }

}