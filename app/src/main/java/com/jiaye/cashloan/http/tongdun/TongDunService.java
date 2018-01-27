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
     * ocr front
     */
    @POST("identification/ocr/v1")
    @FormUrlEncoded
    Flowable<TongDunResponse<TongDunOCRFront>> ocrFront(@Query("partner_code") String code, @Query("partner_key") String key, @Field("image") String imageBase64);

    /**
     * ocr back
     */
    @POST("identification/ocr/v1")
    @FormUrlEncoded
    Flowable<TongDunResponse<TongDunOCRBack>> ocrBack(@Query("partner_code") String code, @Query("partner_key") String key, @Field("image") String imageBase64);

    /**
     * check
     */
    @POST("bodyguard/apply/v4")
    @FormUrlEncoded
    Flowable<TongDunResponse<Object>> check(@Query("partner_code") String code, @Query("partner_key") String key, @Query("app_name") String appName, @Field("biz_code") String bizCode, @Field("id_number") String id, @Field("account_name") String name);

    /**
     * face
     */
    @POST("identification/realimage/v1")
    @FormUrlEncoded
    Flowable<TongDunResponse<TongDunFace>> face(@Query("partner_code") String code, @Query("partner_key") String key, @Field("name") String name, @Field("id_number") String id, @Field("image") String base64, @Field("type") String type);
}
