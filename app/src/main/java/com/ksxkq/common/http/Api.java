package com.ksxkq.common.http;

import com.ksxkq.net.factory.JSONConverterFactory;
import com.ksxkq.net.LoggingInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * OnePiece
 * Created by xukq on 8/24/16.
 */
public class Api {

    private static ApiService sService;

    public static ApiService getDefault() {
        if (sService == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new LoggingInterceptor())
                    .build();
            sService = new Retrofit.Builder()
                    .baseUrl("http://o9vw0ljzd.bkt.clouddn.com")
                    .addConverterFactory(JSONConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(client)
                    .build().create(ApiService.class);
        }
        return sService;
    }

}
