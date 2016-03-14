package me.saket.phonepesaket.data.rxfunctions;

import me.saket.phonepesaket.data.exceptions.HTTPException;
import me.saket.phonepesaket.data.models.ApiResponse;
import retrofit2.Response;
import rx.exceptions.OnErrorThrowable;

/**
 * Extracts the ApiResponse data from the server {@link Response}.
 * Throws an {@link HTTPException} in case of unsuccessful request
 *
 * @param <R> Type of the response class.
 */
public class ExtractRetrofitResponseOrThrowError<R extends ApiResponse> implements
        rx.functions.Func1<Response<R>, R> {

    @Override
    public R call(Response<R> response) {
        if (!response.isSuccess()) {
            throw OnErrorThrowable.from(new HTTPException(response.errorBody()));
        }
        return response.body();
    }

}