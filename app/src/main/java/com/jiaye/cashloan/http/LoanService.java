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
import com.jiaye.cashloan.http.data.loan.Contact;
import com.jiaye.cashloan.http.data.loan.ContactRequest;
import com.jiaye.cashloan.http.data.loan.DefaultProduct;
import com.jiaye.cashloan.http.data.loan.DefaultProductRequest;
import com.jiaye.cashloan.http.data.loan.LoanAuth;
import com.jiaye.cashloan.http.data.loan.LoanAuthRequest;
import com.jiaye.cashloan.http.data.loan.LoanFaceAuth;
import com.jiaye.cashloan.http.data.loan.LoanFaceAuthRequest;
import com.jiaye.cashloan.http.data.loan.LoanIDCardAuth;
import com.jiaye.cashloan.http.data.loan.LoanIDCardAuthRequest;
import com.jiaye.cashloan.http.data.loan.LoanInfoAuth;
import com.jiaye.cashloan.http.data.loan.LoanInfoAuthRequest;
import com.jiaye.cashloan.http.data.loan.LoanUploadPicture;
import com.jiaye.cashloan.http.data.loan.LoanUploadPictureRequest;
import com.jiaye.cashloan.http.data.loan.Person;
import com.jiaye.cashloan.http.data.loan.PersonRequest;
import com.jiaye.cashloan.http.data.loan.SaveContact;
import com.jiaye.cashloan.http.data.loan.SaveContactRequest;
import com.jiaye.cashloan.http.data.loan.SavePerson;
import com.jiaye.cashloan.http.data.loan.SavePersonRequest;

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
     * 借款认证状态
     */
    @POST("userApprove")
    Flowable<Response<LoanAuth>> loanAuth(@Body Request<LoanAuthRequest> request);

    /**
     * 上传图片
     */
    @POST("uploadPic")
    Flowable<Response<LoanUploadPicture>> uploadPicture(@Body Request<LoanUploadPictureRequest> request);

    /**
     * 借款身份证认证
     */
    @POST("cardAuth")
    Flowable<Response<LoanIDCardAuth>> loanIDCardAuth(@Body Request<LoanIDCardAuthRequest> request);

    /**
     * 借款活体检测认证
     */
    @POST("bioAssay")
    Flowable<Response<LoanFaceAuth>> loanFaceAuth(@Body Request<LoanFaceAuthRequest> request);

    /**
     * 个人认证状态
     */
    @POST("userApprove/queryMyApprove")
    Flowable<Response<Auth>> auth(@Body Request<AuthRequest> request);

    /**
     * 个人资料状态
     */
    @POST("client")
    Flowable<Response<LoanInfoAuth>> loanInfoAuth(@Body Request<LoanInfoAuthRequest> request);

    /**
     * 个人信息
     */
    @POST("personalInfo")
    Flowable<Response<Person>> person(@Body Request<PersonRequest> request);

    /**
     * 保存个人信息
     */
    @POST("personalInfo/save")
    Flowable<Response<SavePerson>> savePerson(@Body Request<SavePersonRequest> request);

    /**
     * 联系人信息
     */
    @POST("linkman/queryLinkInfo")
    Flowable<Response<Contact>> contact(@Body Request<ContactRequest> request);

    /**
     * 保存联系人信息
     */
    @POST("linkman")
    Flowable<Response<SaveContact>> saveContact(@Body Request<SaveContactRequest> request);

    /**
     * 校验忘记密码验证码
     */
    @POST("sendMsg/juageCodeTime")
    Flowable<Response<CheckForgetPasswordVerificationCode>> checkForgetPasswordVerificationCode(@Body Request<CheckForgetPasswordVerificationCodeRequest> request);
}
