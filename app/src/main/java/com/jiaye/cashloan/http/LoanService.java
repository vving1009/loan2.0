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
import com.jiaye.cashloan.http.data.loan.CheckLoan;
import com.jiaye.cashloan.http.data.loan.CheckLoanRequest;
import com.jiaye.cashloan.http.data.loan.Contact;
import com.jiaye.cashloan.http.data.loan.ContactRequest;
import com.jiaye.cashloan.http.data.loan.Contract;
import com.jiaye.cashloan.http.data.loan.ContractRequest;
import com.jiaye.cashloan.http.data.loan.DefaultProduct;
import com.jiaye.cashloan.http.data.loan.DefaultProductRequest;
import com.jiaye.cashloan.http.data.loan.LoanAuth;
import com.jiaye.cashloan.http.data.loan.LoanAuthRequest;
import com.jiaye.cashloan.http.data.loan.LoanOpen;
import com.jiaye.cashloan.http.data.loan.LoanOpenRequest;
import com.jiaye.cashloan.http.data.loan.LoanOpenSMS;
import com.jiaye.cashloan.http.data.loan.LoanOpenSMSRequest;
import com.jiaye.cashloan.http.data.loan.LoanConfirm;
import com.jiaye.cashloan.http.data.loan.LoanConfirmInfo;
import com.jiaye.cashloan.http.data.loan.LoanConfirmInfoRequest;
import com.jiaye.cashloan.http.data.loan.LoanConfirmRequest;
import com.jiaye.cashloan.http.data.loan.LoanDetails;
import com.jiaye.cashloan.http.data.loan.LoanDetailsRequest;
import com.jiaye.cashloan.http.data.loan.LoanFaceAuth;
import com.jiaye.cashloan.http.data.loan.LoanFaceAuthRequest;
import com.jiaye.cashloan.http.data.loan.LoanHistory;
import com.jiaye.cashloan.http.data.loan.LoanHistoryRequest;
import com.jiaye.cashloan.http.data.loan.LoanIDCardAuth;
import com.jiaye.cashloan.http.data.loan.LoanIDCardAuthRequest;
import com.jiaye.cashloan.http.data.loan.LoanInfoAuth;
import com.jiaye.cashloan.http.data.loan.LoanInfoAuthRequest;
import com.jiaye.cashloan.http.data.loan.LoanProgress;
import com.jiaye.cashloan.http.data.loan.LoanProgressRequest;
import com.jiaye.cashloan.http.data.loan.LoanUploadPicture;
import com.jiaye.cashloan.http.data.loan.LoanUploadPictureRequest;
import com.jiaye.cashloan.http.data.loan.LoanBindBank;
import com.jiaye.cashloan.http.data.loan.LoanBindBankRequest;
import com.jiaye.cashloan.http.data.loan.Person;
import com.jiaye.cashloan.http.data.loan.PersonRequest;
import com.jiaye.cashloan.http.data.loan.SaveContact;
import com.jiaye.cashloan.http.data.loan.SaveContactRequest;
import com.jiaye.cashloan.http.data.loan.SavePerson;
import com.jiaye.cashloan.http.data.loan.SavePersonRequest;
import com.jiaye.cashloan.http.data.loan.SavePhone;
import com.jiaye.cashloan.http.data.loan.SavePhoneRequest;
import com.jiaye.cashloan.http.data.loan.SaveTaoBao;
import com.jiaye.cashloan.http.data.loan.SaveTaoBaoRequest;
import com.jiaye.cashloan.http.data.loan.Sesame;
import com.jiaye.cashloan.http.data.loan.SesameRequest;
import com.jiaye.cashloan.http.data.loan.WatchContract;
import com.jiaye.cashloan.http.data.loan.WatchContractRequest;
import com.jiaye.cashloan.http.data.my.Bank;
import com.jiaye.cashloan.http.data.my.BankRequest;
import com.jiaye.cashloan.http.data.my.IDCardAuth;
import com.jiaye.cashloan.http.data.my.IDCardAuthRequest;
import com.jiaye.cashloan.http.data.my.Phone;
import com.jiaye.cashloan.http.data.my.PhoneRequest;
import com.jiaye.cashloan.http.data.my.User;
import com.jiaye.cashloan.http.data.my.UserRequest;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

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
     * 我的
     */
    @POST("mine")
    Flowable<Response<User>> user(@Body Request<UserRequest> request);

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
     * 是否可以借款
     */
    @POST("userApprove/ifAgainLend")
    Flowable<Response<CheckLoan>> checkLoan(@Body Request<CheckLoanRequest> request);

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
     * 身份证认证
     */
    @POST("cardAuth/queryIdentityInfo")
    Flowable<Response<IDCardAuth>> idCardAuth(@Body Request<IDCardAuthRequest> request);

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
     * 保存手机信息
     */
    @POST("phoneOperator")
    Flowable<Response<SavePhone>> savePhone(@Body Request<SavePhoneRequest> request);

    /**
     * 手机信息
     */
    @POST("phoneOperator/queryOperatorInfo")
    Flowable<Response<Phone>> phone(@Body Request<PhoneRequest> request);

    /**
     * 保存淘宝信息
     */
    @POST("taobao")
    Flowable<Response<SaveTaoBao>> saveTaoBao(@Body Request<SaveTaoBaoRequest> request);

    /**
     * 芝麻信用分
     */
    @POST("zmfQuery")
    Flowable<Response<Sesame>> sesame(@Body Request<SesameRequest> request);

    /**
     * 借款确认信息
     */
    @POST("loanConfirm")
    Flowable<Response<LoanConfirmInfo>> loanConfirmInfo(@Body Request<LoanConfirmInfoRequest> request);

    /**
     * 借款确认
     */
    @POST("loanConfirm/updateSatus")
    Flowable<Response<LoanConfirm>> loanConfirm(@Body Request<LoanConfirmRequest> request);

    /**
     * 借款详情
     */
    @POST("mine/loanDetail")
    Flowable<Response<LoanDetails>> loanDetails(@Body Request<LoanDetailsRequest> request);

    /**
     * 借款历史
     */
    @POST("lendApply")
    Flowable<Response<LoanHistory>> loanHistory(@Body Request<LoanHistoryRequest> request);

    /**
     * 借款进度
     */
    @POST("loanProgress")
    Flowable<Response<LoanProgress>> loanProgress(@Body Request<LoanProgressRequest> request);

    /**
     * 绑定银行卡
     */
    @POST("bindBankCard")
    Flowable<Response<LoanBindBank>> loanBindBank(@Body Request<LoanBindBankRequest> request);

    /**
     * 银行卡
     */
    @POST("bindBankCard/displayCardInfo")
    Flowable<Response<Bank>> bank(@Body Request<BankRequest> request);

    /**
     * 查看合同
     */
    @POST("compact/cashLoan")
    Flowable<Response<WatchContract>> watchContract(@Body Request<WatchContractRequest> request);

    /**
     * 签订合同
     */
    @POST("compact/confirmCompact")
    Flowable<Response<Contract>> contract(@Body Request<ContractRequest> request);

    /**
     * 校验忘记密码验证码
     */
    @POST("sendMsg/juageCodeTime")
    Flowable<Response<CheckForgetPasswordVerificationCode>> checkForgetPasswordVerificationCode(@Body Request<CheckForgetPasswordVerificationCodeRequest> request);

    /**
     * 签名
     */
    @GET("jxbank/requestSign")
    Flowable<retrofit2.Response<ResponseBody>> sign(@Query(value = "mapJson") String mapJson);

    /**
     * 开户获取验证码
     */
    @POST("jxbank/sendJxMsg")
    Flowable<Response<LoanOpenSMS>> loanOpenSMS(@Body Request<LoanOpenSMSRequest> request);

    /**
     * 开户
     */
    @POST("jxOpen")
    Flowable<Response<LoanOpen>> loanOpen(@Body Request<LoanOpenRequest> request);
}
