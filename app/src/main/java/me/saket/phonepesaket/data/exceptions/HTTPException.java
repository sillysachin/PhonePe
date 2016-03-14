package me.saket.phonepesaket.data.exceptions;

import okhttp3.ResponseBody;

/**
 * Thrown when the server could not be reached (network error) or the server
 * returned a non-200 HTTP code.
 */
public class HTTPException extends Exception {

    /**
     * ResponseBody, generated by Retrofit.
     */
    private ResponseBody mResponseBody;

    public HTTPException(ResponseBody responseBody) {
        this.mResponseBody = responseBody;
    }

    public ResponseBody getResponseBody() {
        return mResponseBody;
    }

}
