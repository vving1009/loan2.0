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

    /**
     * auth
     */
    @POST("auth/v2/get_auth_token")
    Flowable<GongXinBaoResponse<GongXinBaoToken>> auth(@Body GongXinBaoTokenRequest request);

    /**
     * operators auth
     */
    @GET("auth/operator/v2/phone_config/{token}/{phone}")
    Flowable<GongXinBaoResponse<GongXinBaoOperators>> operatorsAuth(@Path("token") String token, @Path("phone") String phone);

    /*获取短信验证码*/
    @GET("auth/operator/v2/refresh_sms_code/{token}")
    Flowable<GongXinBaoResponse<GongXinBaoAuth>> refreshSmsCode(@Path("token") String token);

    /*获取图形验证码*/
    @GET("auth/operator/v2/refresh_verify_code/{token}")
    Flowable<GongXinBaoResponse<GongXinBaoAuth>> refreshVerifyCode(@Path("token") String token);

    /*登录*/
    @POST("auth/operator/v2/login_submit/{token}")
    Flowable<GongXinBaoResponse<GongXinBaoAuth>> login(@Path("token") String token, @Body GongXinBaoSubmitRequest request);

    /*二次验证码提交*/
    @POST("auth/operator/v2/code_submit/{token}")
    Flowable<GongXinBaoResponse<GongXinBaoAuth>> second(@Path("token") String token, @Body GongXinBaoSubmitRequest request);

    /*获取任务状态*/
    @GET("auth/operator/v2/get_status/{token}")
    Flowable<GongXinBaoResponse<GongXinBaoAuth>> getLoginStatus(@Path("token") String token);
}
