package me.saket.phonepesaket.data.rxfunctions;

import java.util.List;

import me.saket.phonepesaket.data.exceptions.InvalidServerResponseException;
import me.saket.phonepesaket.data.models.Transaction;
import me.saket.phonepesaket.data.models.TransactionSyncResponse;
import rx.exceptions.OnErrorThrowable;

import static me.saket.phonepesaket.utils.Collections.isEmpty;

/**
 * Throws an InvalidServerResponse if the server did not return any transactions.
 * Otherwise, returns the received transactions.
 *
 * @param <R> Type of the response class.
 */
public class ExtractTransactionsOrThrowError<R extends TransactionSyncResponse> implements
        rx.functions.Func1<R, List<Transaction>> {

    @Override
    public List<Transaction> call(R response) {
        if (isEmpty(response.getTransactions())) {
            throw OnErrorThrowable.from(new InvalidServerResponseException());

        }
        return response.getTransactions();
    }

}