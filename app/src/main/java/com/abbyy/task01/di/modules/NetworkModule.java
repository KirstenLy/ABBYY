package com.abbyy.task01.di.modules;

import android.content.Context;

import com.abbyy.task01.Storage;
import com.abbyy.task01.data.network.LingvoApi;
import com.abbyy.task01.data.network.UnsafeOkHttpClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import timber.log.Timber;

@Module
public class NetworkModule {

    private Context context;
    private final String BASE_URL = "https://developers.lingvolive.com/";

    public NetworkModule(Context context) {
        this.context = context;
    }

    private HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message ->
            Timber.e(message))
            .setLevel(HttpLoggingInterceptor.Level.BODY);


    private Interceptor signingInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Storage storage = new Storage(context);
            if (storage.getBearerToken() != null) {
                Request request = chain.request();
                request = request.newBuilder().addHeader("Authorization", "Bearer " + storage.getBearerToken()).build();
                return chain.proceed(request);
            } else {
                return chain.proceed(chain.request());
            }
        }
    };

    private OkHttpClient client = UnsafeOkHttpClient.getUnsafeOkHttpClient().newBuilder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(signingInterceptor)
            .build();

    private Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(init()))
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(BASE_URL)
            .client(client)
            .build();


    private static Gson init() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setLenient();
        return gsonBuilder.create();
    }

    @Provides
    LingvoApi providesLingvoApi() {
        return lingvoApi;
    }
    private LingvoApi lingvoApi = retrofit.create(LingvoApi.class);
}
