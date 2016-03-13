package me.saket.phonepesaket.services;

import me.saket.phonepesaket.data.models.TransactionSyncResponse;
import retrofit2.Response;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Defines APIs that the app uses for communicating with the server.
 */
public interface ServerApiService {

    String BASE_ENDPOINT = "http://stage.phonepe.com/";
    String JSON_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    @GET("transaction/request?userId=3")
    Observable<Response<TransactionSyncResponse>> getPendingTransactions();

    @GET("transaction/request?userId=4")
    Observable<Response<TransactionSyncResponse>> getPastTransactions();

}