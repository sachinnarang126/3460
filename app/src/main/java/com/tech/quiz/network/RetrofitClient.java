package com.tech.quiz.network;


import com.tech.BuildConfig;
import com.tech.quiz.util.Constant;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit mRetrofit;

    private static OkHttpClient getClient() {
        OkHttpClient client;
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        } else {
            client = new OkHttpClient.Builder().build();
        }
        OkHttpClient.Builder builder = client.newBuilder().readTimeout(40, TimeUnit.SECONDS).connectTimeout(40, TimeUnit.SECONDS);
        return builder.build();
    }

    public static RetrofitApiService getRetrofitClient() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getClient())
                    .build();
        }
        return mRetrofit.create(RetrofitApiService.class);
    }
}
