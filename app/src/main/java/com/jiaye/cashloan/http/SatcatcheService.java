package com.jiaye.cashloan.http;

import com.jiaye.cashloan.http.base.SatcatcheRequest;
import com.jiaye.cashloan.http.base.SatcatcheResponse;
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
import com.jiaye.cashloan.http.data.dictionary.DictionaryList;
import com.jiaye.cashloan.http.data.dictionary.DictionaryListRequest;
import com.jiaye.cashloan.http.data.home.BannerList;
import com.jiaye.cashloan.http.data.home.BannerListRequest;
import com.jiaye.cashloan.http.data.home.ProductList;
import com.jiaye.cashloan.http.data.home.ProductListRequest;
import com.jiaye.cashloan.http.data.launch.CheckUpdate;
import com.jiaye.cashloan.http.data.launch.CheckUpdateRequest;
import com.jiaye.cashloan.http.data.loan.CheckLoan;
import com.jiaye.cashloan.http.data.loan.CheckLoanRequest;
import com.jiaye.cashloan.http.data.loan.Contact;
import com.jiaye.cashloan.http.data.loan.ContactRequest;
import com.jiaye.cashloan.http.data.loan.ContractList;
import com.jiaye.cashloan.http.data.loan.ContractListRequest;
import com.jiaye.cashloan.http.data.loan.FileState;
import com.jiaye.cashloan.http.data.loan.FileStateRequest;
import com.jiaye.cashloan.http.data.loan.Loan;
import com.jiaye.cashloan.http.data.loan.LoanApply;
import com.jiaye.cashloan.http.data.loan.LoanApplyRequest;
import com.jiaye.cashloan.http.data.loan.LoanAuth;
import com.jiaye.cashloan.http.data.loan.LoanAuthRequest;
import com.jiaye.cashloan.http.data.loan.LoanBindBank;
import com.jiaye.cashloan.http.data.loan.LoanBindBankRequest;
import com.jiaye.cashloan.http.data.loan.LoanConfirm;
import com.jiaye.cashloan.http.data.loan.LoanConfirmInfo;
import com.jiaye.cashloan.http.data.loan.LoanConfirmInfoRequest;
import com.jiaye.cashloan.http.data.loan.LoanConfirmRequest;
import com.jiaye.cashloan.http.data.loan.LoanFaceAuth;
import com.jiaye.cashloan.http.data.loan.LoanFaceAuthRequest;
import com.jiaye.cashloan.http.data.loan.LoanIDCardAuth;
import com.jiaye.cashloan.http.data.loan.LoanIDCardAuthRequest;
import com.jiaye.cashloan.http.data.loan.LoanInfoAuth;
import com.jiaye.cashloan.http.data.loan.LoanInfoAuthRequest;
import com.jiaye.cashloan.http.data.loan.LoanPlan;
import com.jiaye.cashloan.http.data.loan.LoanPlanRequest;
import com.jiaye.cashloan.http.data.loan.LoanProgress;
import com.jiaye.cashloan.http.data.loan.LoanProgressRequest;
import com.jiaye.cashloan.http.data.loan.LoanRequest;
import com.jiaye.cashloan.http.data.loan.LoanUploadPicture;
import com.jiaye.cashloan.http.data.loan.LoanUploadPictureRequest;
import com.jiaye.cashloan.http.data.loan.Person;
import com.jiaye.cashloan.http.data.loan.PersonRequest;
import com.jiaye.cashloan.http.data.loan.RiskAppList;
import com.jiaye.cashloan.http.data.loan.RiskAppListRequest;
import com.jiaye.cashloan.http.data.loan.SaveContact;
import com.jiaye.cashloan.http.data.loan.SaveContactRequest;
import com.jiaye.cashloan.http.data.loan.SavePerson;
import com.jiaye.cashloan.http.data.loan.SavePersonRequest;
import com.jiaye.cashloan.http.data.loan.SavePhone;
import com.jiaye.cashloan.http.data.loan.SavePhoneRequest;
import com.jiaye.cashloan.http.data.loan.SaveTaoBao;
import com.jiaye.cashloan.http.data.loan.SaveTaoBaoRequest;
import com.jiaye.cashloan.http.data.loan.UploadContact;
import com.jiaye.cashloan.http.data.loan.UploadContactRequest;
import com.jiaye.cashloan.http.data.loan.UploadFile;
import com.jiaye.cashloan.http.data.loan.UploadFileRequest;
import com.jiaye.cashloan.http.data.loan.UploadLocation;
import com.jiaye.cashloan.http.data.loan.UploadLocationRequest;
import com.jiaye.cashloan.http.data.loan.UploadRiskAppList;
import com.jiaye.cashloan.http.data.loan.UploadRiskAppListRequest;
import com.jiaye.cashloan.http.data.loan.Visa;
import com.jiaye.cashloan.http.data.loan.VisaRequest;
import com.jiaye.cashloan.http.data.my.IDCardAuth;
import com.jiaye.cashloan.http.data.my.IDCardAuthRequest;
import com.jiaye.cashloan.http.data.my.Phone;
import com.jiaye.cashloan.http.data.my.PhoneRequest;
import com.jiaye.cashloan.http.data.my.User;
import com.jiaye.cashloan.http.data.my.UserRequest;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * SatcatcheService
 *
 * @author 贾博瑄
 */
public interface SatcatcheService {

    /**
     * 获取通用字典
     */
    @POST("dictEntry")
    Flowable<SatcatcheResponse<DictionaryList>> dictionaryList(@Body SatcatcheRequest<DictionaryListRequest> request);

    /**
     * 检测升级
     */
    @POST("getVersion")
    Flowable<SatcatcheResponse<CheckUpdate>> checkUpdate(@Body SatcatcheRequest<CheckUpdateRequest> request);

    /**
     * 验证码
     */
    @POST("sendMsg")
    Flowable<SatcatcheResponse<VerificationCode>> verificationCode(@Body SatcatcheRequest<VerificationCodeRequest> request);

    /**
     * 注册
     */
    @POST("register")
    Flowable<SatcatcheResponse<Register>> register(@Body SatcatcheRequest<RegisterRequest> request);

    /**
     * 登录
     */
    @POST("login")
    Flowable<SatcatcheResponse<Login>> login(@Body SatcatcheRequest<LoginRequest> request);

    /**
     * 修改密码
     */
    @POST("changePwd")
    Flowable<SatcatcheResponse<ChangePassword>> changePassword(@Body SatcatcheRequest<ChangePasswordRequest> request);

    /**
     * 我的
     */
    @POST("mine")
    Flowable<SatcatcheResponse<User>> user(@Body SatcatcheRequest<UserRequest> request);

    /**
     * 首页商品轮播
     */
    @POST("carouselPicture")
    Flowable<SatcatcheResponse<BannerList>> bannerList(@Body SatcatcheRequest<BannerListRequest> request);

    /**
     * 首页产品列表
     */
    @POST("pictureinfo")
    Flowable<SatcatcheResponse<ProductList>> productList(@Body SatcatcheRequest<ProductListRequest> request);

    /**
     * 是否可以借款
     */
    @POST("userApprove/ifAgainLend")
    Flowable<SatcatcheResponse<CheckLoan>> checkLoan(@Body SatcatcheRequest<CheckLoanRequest> request);

    /**
     * 我要借款
     */
    @POST("queryJlaId")
    Flowable<SatcatcheResponse<Loan>> loan(@Body SatcatcheRequest<LoanRequest> request);

    /**
     * 获取风险应用列表
     */
    @POST("androidAppList")
    Flowable<SatcatcheResponse<RiskAppList>> riskAppList(@Body SatcatcheRequest<RiskAppListRequest> request);

    /**
     * 上传风险应用安装情况
     */
    @POST("saveAppList")
    Flowable<SatcatcheResponse<UploadRiskAppList>> uploadRiskAppList(@Body SatcatcheRequest<UploadRiskAppListRequest> request);

    /**
     * 借款认证状态
     */
    @POST("userApprove")
    Flowable<SatcatcheResponse<LoanAuth>> loanAuth(@Body SatcatcheRequest<LoanAuthRequest> request);

    /**
     * 上传图片
     */
    @POST("uploadPic")
    Flowable<SatcatcheResponse<LoanUploadPicture>> uploadPicture(@Body SatcatcheRequest<LoanUploadPictureRequest> request);

    /**
     * 借款身份证认证
     */
    @POST("cardAuth")
    Flowable<SatcatcheResponse<LoanIDCardAuth>> loanIDCardAuth(@Body SatcatcheRequest<LoanIDCardAuthRequest> request);

    /**
     * 身份证认证
     */
    @POST("cardAuth/queryIdentityInfo")
    Flowable<SatcatcheResponse<IDCardAuth>> idCardAuth(@Body SatcatcheRequest<IDCardAuthRequest> request);

    /**
     * 借款活体检测认证
     */
    @POST("bioAssay")
    Flowable<SatcatcheResponse<LoanFaceAuth>> loanFaceAuth(@Body SatcatcheRequest<LoanFaceAuthRequest> request);

    /**
     * 个人认证状态
     */
    @POST("userApprove/queryMyApprove")
    Flowable<SatcatcheResponse<Auth>> auth(@Body SatcatcheRequest<AuthRequest> request);

    /**
     * 个人资料状态
     */
    @POST("client")
    Flowable<SatcatcheResponse<LoanInfoAuth>> loanInfoAuth(@Body SatcatcheRequest<LoanInfoAuthRequest> request);

    /**
     * 个人信息
     */
    @POST("personalInfo")
    Flowable<SatcatcheResponse<Person>> person(@Body SatcatcheRequest<PersonRequest> request);

    /**
     * 保存个人信息
     */
    @POST("personalInfo/save")
    Flowable<SatcatcheResponse<SavePerson>> savePerson(@Body SatcatcheRequest<SavePersonRequest> request);

    /**
     * 联系人信息
     */
    @POST("linkman/queryLinkInfo")
    Flowable<SatcatcheResponse<Contact>> contact(@Body SatcatcheRequest<ContactRequest> request);

    /**
     * 保存联系人信息
     */
    @POST("linkman")
    Flowable<SatcatcheResponse<SaveContact>> saveContact(@Body SatcatcheRequest<SaveContactRequest> request);

    /**
     * 保存手机信息
     */
    @POST("phoneOperator")
    Flowable<SatcatcheResponse<SavePhone>> savePhone(@Body SatcatcheRequest<SavePhoneRequest> request);

    /**
     * 手机信息
     */
    @POST("phoneOperator/queryOperatorInfo")
    Flowable<SatcatcheResponse<Phone>> phone(@Body SatcatcheRequest<PhoneRequest> request);

    /**
     * 签名
     */
    @POST("eSign")
    Flowable<SatcatcheResponse<Visa>> visa(@Body SatcatcheRequest<VisaRequest> request);

    /**
     * 保存淘宝信息
     */
    @POST("taobao")
    Flowable<SatcatcheResponse<SaveTaoBao>> saveTaoBao(@Body SatcatcheRequest<SaveTaoBaoRequest> request);

    /**
     * 借款确认信息
     */
    @POST("loanConfirm")
    Flowable<SatcatcheResponse<LoanConfirmInfo>> loanConfirmInfo(@Body SatcatcheRequest<LoanConfirmInfoRequest> request);

    /**
     * 借款确认
     */
    @POST("updateStatus")
    Flowable<SatcatcheResponse<LoanConfirm>> loanConfirm(@Body SatcatcheRequest<LoanConfirmRequest> request);

    /**
     * 借款进度
     */
    @POST("loanProgress")
    Flowable<SatcatcheResponse<LoanProgress>> loanProgress(@Body SatcatcheRequest<LoanProgressRequest> request);

    /**
     * 申请借款
     */
    @POST("shjk")
    Flowable<SatcatcheResponse<LoanApply>> loanApply(@Body SatcatcheRequest<LoanApplyRequest> request);

    /**
     * 还款列表
     */
    @POST("shjk/repaymentPlans")
    Flowable<SatcatcheResponse<LoanPlan>> loanPlan(@Body SatcatcheRequest<LoanPlanRequest> request);

    /**
     * 绑定银行卡
     */
    @POST("bindBankCard")
    Flowable<SatcatcheResponse<LoanBindBank>> loanBindBank(@Body SatcatcheRequest<LoanBindBankRequest> request);

    /**
     * 查看合同列表
     */
    @POST("shCompactShow")
    Flowable<SatcatcheResponse<ContractList>> contractList(@Body SatcatcheRequest<ContractListRequest> request);

    /**
     * 校验忘记密码验证码
     */
    @POST("sendMsg/juageCodeTime")
    Flowable<SatcatcheResponse<CheckForgetPasswordVerificationCode>> checkForgetPasswordVerificationCode(@Body SatcatcheRequest<CheckForgetPasswordVerificationCodeRequest> request);

    /**
     * 认证材料查询
     */
    @POST("searchMaterialPic")
    Flowable<SatcatcheResponse<FileState>> fileState(@Body SatcatcheRequest<FileStateRequest> request);

    /**
     * 上传认证材料
     */
    @POST("uploadMaterialPic")
    Flowable<SatcatcheResponse<UploadFile>> uploadFile(@Body SatcatcheRequest<UploadFileRequest> request);

    /**
     * 上传通讯录
     */
    @POST("phoneContacts")
    Flowable<SatcatcheResponse<UploadContact>> uploadContact(@Body SatcatcheRequest<UploadContactRequest> request);

    /**
     * 上传地理位置
     */
    @POST("phoneLocation")
    Flowable<SatcatcheResponse<UploadLocation>> uploadLocation(@Body SatcatcheRequest<UploadLocationRequest> request);
}
