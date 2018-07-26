package com.satcatche.appupgrade.checkupgrade;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.satcatche.appupgrade.utils.UpgradeConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * CheckUpgradeClient
 *
 * @author 贾博瑄
 */
public enum CheckUpgradeClient {

    INSTANCE;

    private CheckUpgradeService mService;

    CheckUpgradeClient() {
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
                .baseUrl(UpgradeConfig.getCheckUpgradeUrl())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
        mService = retrofit.create(CheckUpgradeService.class);
    }

    public CheckUpgradeService getService() {
        return mService;
    }
}
