package me.saket.phonepesaket.data.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Response received from the server when syncing transactions (pending and completed).
 */
public class TransactionSyncResponse implements ApiResponse {

    @SerializedName("transactionRequests")
    public List<Transaction> transactions;

    public TransactionSyncResponse() {
        // Reqd. by Gson
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

}