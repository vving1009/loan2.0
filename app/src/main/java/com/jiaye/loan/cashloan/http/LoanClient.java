package com.jiaye.loan.cashloan.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jiaye.loan.cashloan.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * LoanClient
 *
 * @author 贾博瑄
 */

public enum LoanClient {

    INSTANCE;

    private LoanService mService;

    LoanClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new LoggingInterceptor());
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        Gson gson = gsonBuilder.create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
        mService = retrofit.create(LoanService.class);
    }

    public LoanService getService() {
        return mService;
    }

    public void setService(LoanService service) {
        mService = service;
    }
}
