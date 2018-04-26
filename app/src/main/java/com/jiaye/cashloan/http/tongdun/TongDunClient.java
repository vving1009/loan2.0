package com.jiaye.cashloan.http.tongdun;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jiaye.cashloan.BuildConfig;
import com.jiaye.cashloan.http.LoggingInterceptor;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * TongDunClient
 *
 * @author 贾博瑄
 */

public enum TongDunClient {

    INSTANCE;

    private TongDunService mService;

    TongDunClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new LoggingInterceptor());
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        Gson gson = gsonBuilder
                .registerTypeAdapter(TongDunFace.class, new TongDunOCRAdapter())
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.TONGDUN_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
        mService = retrofit.create(TongDunService.class);
    }

    public TongDunService getService() {
        return mService;
    }
}
