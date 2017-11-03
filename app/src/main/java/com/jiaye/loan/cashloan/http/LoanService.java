package com.jiaye.loan.cashloan.http;

import com.jiaye.loan.cashloan.http.base.Request;
import com.jiaye.loan.cashloan.http.base.Response;
import com.jiaye.loan.cashloan.http.data.auth.VerificationCode;
import com.jiaye.loan.cashloan.http.data.auth.VerificationCodeRequest;
import com.jiaye.loan.cashloan.http.data.auth.login.Login;
import com.jiaye.loan.cashloan.http.data.auth.login.LoginRequest;
import com.jiaye.loan.cashloan.http.data.auth.password.ChangePassword;
import com.jiaye.loan.cashloan.http.data.auth.password.ChangePasswordRequest;
import com.jiaye.loan.cashloan.http.data.auth.password.CheckForgetPasswordVerificationCode;
import com.jiaye.loan.cashloan.http.data.auth.password.CheckForgetPasswordVerificationCodeRequest;
import com.jiaye.loan.cashloan.http.data.auth.register.Register;
import com.jiaye.loan.cashloan.http.data.auth.register.RegisterRequest;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * LoanService
 *
 * @author 贾博瑄
 */

public interface LoanService {

    /**
     * 验证码
     */
    @POST("sendMsg")
    Observable<Response<VerificationCode>> verificationCode(@Body Request<VerificationCodeRequest> request);

    /**
     * 注册
     */
    @POST("register")
    Observable<Response<Register>> register(@Body Request<RegisterRequest> request);

    /**
     * 登录
     */
    @POST("login")
    Observable<Response<Login>> login(@Body Request<LoginRequest> request);

    /**
     * 校验忘记密码验证码
     */
    @POST("sendMsg/juageCodeTime")
    Observable<Response<CheckForgetPasswordVerificationCode>> checkForgetPasswordVerificationCode(@Body Request<CheckForgetPasswordVerificationCodeRequest> request);

    /**
     * 修改密码
     */
    @POST("changePwd")
    Observable<Response<ChangePassword>> changePassword(@Body Request<ChangePasswordRequest> request);
}
