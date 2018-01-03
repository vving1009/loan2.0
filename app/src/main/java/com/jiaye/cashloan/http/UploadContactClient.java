package com.jiaye.cashloan.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jiaye.cashloan.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * UploadContactClient
 *
 * @author 贾博瑄
 */

public enum  UploadContactClient {

    INSTANCE;

    private UploadContactService mService;

    UploadContactClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new LoggingInterceptor());
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        Gson gson = gsonBuilder
                .registerTypeAdapter(String.class, new StringNullAdapter())
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.UPLOAD_CONTACT_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
        mService = retrofit.create(UploadContactService.class);
    }

    public UploadContactService getService() {
        return mService;
    }
}
