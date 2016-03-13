package me.saket.phonepesaket.services;

import android.support.annotation.NonNull;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import io.realm.RealmObject;
import me.saket.phonepesaket.data.models.TransactionSyncResponse;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Handles making all API calls to the server. Additionally, can add headers like auth-token, etc.
 */
public class PhonePeApi {

    private static PhonePeApi sPhonePeApi;
    private ServerApiService mApiService;

    /**
     * Maximum time to wait before giving up (for connecting to a
     * server as well as waiting for its response).
     */
    long TIMEOUT_DELAY_SECS = 60;   // 60s

    public static PhonePeApi getInstance() {
        if (sPhonePeApi == null) {
            sPhonePeApi = new PhonePeApi();
        }
        return sPhonePeApi;
    }

    public PhonePeApi() {
        // Enable logging
        final HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(TIMEOUT_DELAY_SECS, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT_DELAY_SECS, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServerApiService.BASE_ENDPOINT)
                .client(okHttpClient)
                .addConverterFactory(getJsonConverterFactor())  // Json converter for parsing data
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .validateEagerly(true)
                .build();

        mApiService = retrofit.create(ServerApiService.class);
    }

    /**
     * Returns a GSON converter that Retrofit will use for parsing
     * request / server-response
     */
    @NonNull
    private GsonConverterFactory getJsonConverterFactor() {
        final Gson realmCompatibleGson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    // Realm workaround
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getDeclaringClass().equals(RealmObject.class);
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .excludeFieldsWithoutExposeAnnotation()
                .setDateFormat(ServerApiService.JSON_DATE_FORMAT)
                .create();
        return GsonConverterFactory.create(realmCompatibleGson);
    }

    /**
     * Gets pending transactions for the current user.
     */
    public Observable<Response<TransactionSyncResponse>> getPendingTransactions() {
        return mApiService.getPendingTransactions();
    }

    /**
     * Gets the entire list of completed transactions for the current user.
     */
    public Observable<Response<TransactionSyncResponse>> getPastTransactions() {
        return mApiService.getPastTransactions();
    }

}