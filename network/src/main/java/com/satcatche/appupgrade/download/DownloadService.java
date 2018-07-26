package com.satcatche.appupgrade.download;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * DownloadService
 *
 * @author 贾博瑄
 */

public interface DownloadService {

    @Streaming
    @GET
    Flowable<ResponseBody> download(@Url String url);
}
