package com.jiaye.cashloan.http;

import com.jiaye.cashloan.http.base.Request;
import com.jiaye.cashloan.http.base.Response;
import com.jiaye.cashloan.http.data.auth.Auth;
import com.jiaye.cashloan.http.data.auth.AuthRequest;
import com.jiaye.cashloan.http.data.auth.VerificationCode;
import com.jiaye.cashloan.http.data.auth.VerificationCodeRequest;
import com.jiaye.cashloan.http.data.auth.login.Login;
import com.jiaye.cashloan.http.data.auth.login.LoginRequest;
import com.jiaye.cashloan.http.data.auth.password.ChangePassword;
import com.jiaye.cashloan.http.data.auth.password.ChangePasswordRequest;
import com.jiaye.cashloan.http.data.auth.password.CheckForgetPasswordVerificationCode;
import com.jiaye.cashloan.http.data.auth.password.CheckForgetPasswordVerificationCodeRequest;
import com.jiaye.cashloan.http.data.auth.register.Register;
import com.jiaye.cashloan.http.data.auth.register.RegisterRequest;
import com.jiaye.cashloan.http.data.dictionary.DictionaryRequest;
import com.jiaye.cashloan.http.data.home.ProductList;
import com.jiaye.cashloan.http.data.home.ProductRequest;
import com.jiaye.cashloan.http.data.loan.DefaultProduct;
import com.jiaye.cashloan.http.data.loan.DefaultProductRequest;
import com.jiaye.cashloan.http.data.loan.LoanAuth;
import com.jiaye.cashloan.http.data.loan.LoanAuthRequest;
import com.jiaye.cashloan.http.data.person.Person;
import com.jiaye.cashloan.http.data.person.PersonRequest;
import com.jiaye.cashloan.http.data.person.SavePerson;
import com.jiaye.cashloan.http.data.person.SavePersonRequest;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * LoanService
 *
 * @author 贾博瑄
 */

public interface LoanService {

    /**
     * 获取通用字典
     */
    @POST("dictEntry")
    Flowable<ResponseBody> dictCommon(@Body DictionaryRequest request);

    /**
     * 验证码
     */
    @POST("sendMsg")
    Flowable<Response<VerificationCode>> verificationCode(@Body Request<VerificationCodeRequest> request);

    /**
     * 注册
     */
    @POST("register")
    Flowable<Response<Register>> register(@Body Request<RegisterRequest> request);

    /**
     * 登录
     */
    @POST("login")
    Flowable<Response<Login>> login(@Body Request<LoginRequest> request);

    /**
     * 修改密码
     */
    @POST("changePwd")
    Flowable<Response<ChangePassword>> changePassword(@Body Request<ChangePasswordRequest> request);

    /**
     * 产品列表
     */
    @POST("product")
    Flowable<Response<ProductList>> productList(@Body Request<ProductRequest> request);

    /**
     * 默认产品
     */
    @POST("defaultPro")
    Flowable<Response<DefaultProduct>> defaultProduct(@Body Request<DefaultProductRequest> request);

    /**
     * 产品认证状态
     */
    @POST("userApprove")
    Flowable<Response<LoanAuth>> loanAuth(@Body Request<LoanAuthRequest> request);

    /**
     * 个人认证状态
     */
    @POST("userApprove/queryMyApprove")
    Flowable<Response<Auth>> auth(@Body Request<AuthRequest> request);

    /**
     * 个人资料
     */
    @POST("personalInfo")
    Flowable<Response<Person>> person(@Body Request<PersonRequest> request);

    /**
     * 保存个人资料
     */
    @POST("personalInfo/save")
    Flowable<Response<SavePerson>> savePerson(@Body Request<SavePersonRequest> request);

    /**
     * 校验忘记密码验证码
     */
    @POST("sendMsg/juageCodeTime")
    Flowable<Response<CheckForgetPasswordVerificationCode>> checkForgetPasswordVerificationCode(@Body Request<CheckForgetPasswordVerificationCodeRequest> request);

}
