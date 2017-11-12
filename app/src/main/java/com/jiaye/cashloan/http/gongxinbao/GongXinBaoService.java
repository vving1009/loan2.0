package com.jiaye.cashloan.http.gongxinbao;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * GongXinBaoService
 *
 * @author 贾博瑄
 */

public interface GongXinBaoService {

    /*授权*/
    @POST("auth/v2/get_auth_token")
    Flowable<GongXinBaoResponse<GongXinBaoAuth>> auth(@Body GongXinBaoTokenRequest request);

    /*运营商配置*/
    @GET("auth/operator/v2/phone_config/{token}/{phone}")
    Flowable<GongXinBaoResponse<GongXinBaoOperatorsConfig>> operatorsConfig(@Path("token") String token, @Path("phone") String phone);

    /*运营商短信验证码*/
    @GET("auth/operator/v2/refresh_sms_code/{token}")
    Flowable<GongXinBaoResponse<GongXinBao>> refreshOperatorSmsCode(@Path("token") String token);

    /*运营商图形验证码*/
    @GET("auth/operator/v2/refresh_verify_code/{token}")
    Flowable<GongXinBaoResponse<GongXinBao>> refreshOperatorVerifyCode(@Path("token") String token);

    /*运营商登录*/
    @POST("auth/operator/v2/login_submit/{token}")
    Flowable<GongXinBaoResponse<GongXinBao>> operatorLogin(@Path("token") String token, @Body GongXinBaoSubmitRequest request);

    /*运营商二次验证码提交*/
    @POST("auth/operator/v2/code_submit/{token}")
    Flowable<GongXinBaoResponse<GongXinBao>> operatorSecond(@Path("token") String token, @Body GongXinBaoSubmitRequest request);

    /*获取运营商任务状态*/
    @GET("auth/operator/v2/get_status/{token}")
    Flowable<GongXinBaoResponse<GongXinBao>> getOperatorLoginStatus(@Path("token") String token);

    /*淘宝初始化*/
    @GET("auth/ecommerce/v2/init_config/taobao/{token}")
    Flowable<GongXinBaoResponse<Object>> ecommerceConfig(@Path("token") String token);

    /*淘宝登录*/
    @POST("auth/ecommerce/v2/login_submit/{token}")
    Flowable<GongXinBaoResponse<GongXinBao>> ecommerceLogin(@Path("token") String token, @Body GongXinBaoSubmitRequest request);

    /*淘宝刷新二维码*/
    @GET("auth/ecommerce/v2/refresh_qr_code/{token}")
    Flowable<GongXinBaoResponse<GongXinBao>> refreshEcommerceQRCode(@Path("token") String token);

    /*淘宝二次验证码提交*/
    @POST("auth/ecommerce/v2/code_submit/{token}")
    Flowable<GongXinBaoResponse<GongXinBao>> ecommerceSecond(@Path("token") String token, @Body GongXinBaoSubmitRequest request);

    /*获取淘宝任务状态*/
    @GET("auth/ecommerce/v2/get_status/{token}")
    Flowable<GongXinBaoResponse<GongXinBao>> getEcommerceLoginStatus(@Path("token") String token);

    /*淘宝短信验证码*/
    @GET("auth/ecommerce/v2/refresh_sms_code/{token}")
    Flowable<GongXinBaoResponse<GongXinBao>> refreshEcommerceSmsCode(@Path("token") String token);

    /*淘宝图形验证码*/
    @GET("auth/ecommerce/v2/refresh_verify_code/{token}")
    Flowable<GongXinBaoResponse<GongXinBao>> refreshEcommerceVerifyCode(@Path("token") String token);

}
