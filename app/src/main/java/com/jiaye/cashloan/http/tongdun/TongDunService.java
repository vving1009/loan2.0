package com.jiaye.cashloan.http.tongdun;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * TongDunService
 *
 * @author 贾博瑄
 */

public interface TongDunService {

    /**
     * ocr
     */
    @POST("ocr/v1")
    @FormUrlEncoded
    Flowable<TongDunResponse<TongDunOCRFront>> ocrFront(@Query("partner_code") String code, @Query("partner_key") String key, @Field("image") String imageBase64);

    /**
     * ocr
     */
    @POST("ocr/v1")
    @FormUrlEncoded
    Flowable<TongDunResponse<TongDunOCRBack>> ocrBack(@Query("partner_code") String code, @Query("partner_key") String key, @Field("image") String imageBase64);
}
