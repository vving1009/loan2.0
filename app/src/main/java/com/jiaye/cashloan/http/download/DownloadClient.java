package com.jiaye.cashloan.http.download;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * DownloadClient
 *
 * @author 贾博瑄
 */

public class DownloadClient {

    private DownloadService downloadService;

    public DownloadClient(String url) {
        this(url, null);
    }

    public DownloadClient(String url, DownloadProgressListener listener) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (listener != null) {
            builder.addInterceptor(new DownloadProgressInterceptor(listener));
        }
        builder.retryOnConnectionFailure(true);
        builder.readTimeout(30, TimeUnit.MINUTES);
        OkHttpClient client = builder.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
        downloadService = retrofit.create(DownloadService.class);
    }

    public DownloadService getDownloadService() {
        return downloadService;
    }
}
