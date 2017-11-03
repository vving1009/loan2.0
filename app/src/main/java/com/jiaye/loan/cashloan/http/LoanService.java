package com.jiaye.loan.cashloan.http;

import com.jiaye.loan.cashloan.http.base.Request;
import com.jiaye.loan.cashloan.http.base.Response;
import com.jiaye.loan.cashloan.http.data.auth.login.Login;
import com.jiaye.loan.cashloan.http.data.auth.login.LoginRequest;
import com.jiaye.loan.cashloan.http.data.auth.password.ChangePassword;
import com.jiaye.loan.cashloan.http.data.auth.password.ChangePasswordRequest;
import com.jiaye.loan.cashloan.http.data.auth.register.Register;
import com.jiaye.loan.cashloan.http.data.auth.register.RegisterRequest;
import com.jiaye.loan.cashloan.http.data.auth.register.RegisterVerificationCode;
import com.jiaye.loan.cashloan.http.data.auth.register.RegisterVerificationCodeRequest;

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
     * 注册验证码
     */
    @POST("sendMsg")
    Observable<Response<RegisterVerificationCode>> requestRegisterVerificationCode(@Body Request<RegisterVerificationCodeRequest> request);

    /**
     * 注册
     */
    @POST("register")
    Observable<Response<Register>> requestRegister(@Body Request<RegisterRequest> request);

    /**
     * 登录
     */
    @POST("login")
    Observable<Response<Login>> requestLogin(@Body Request<LoginRequest> request);

    /**
     * 修改密码
     */
    @POST("changePwd")
    Observable<Response<ChangePassword>> requestChangePassword(@Body Request<ChangePasswordRequest> request);
}
