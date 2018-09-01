package com.jiaye.cashloan.http;

import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.base.SatcatcheRequest;
import com.jiaye.cashloan.http.base.SatcatcheResponse;
import com.jiaye.cashloan.http.data.bindbank.SaveBankCardRequest;
import com.jiaye.cashloan.http.data.bioassay.Bioassay;
import com.jiaye.cashloan.http.data.bioassay.BioassayRequest;
import com.jiaye.cashloan.http.data.bioassay.BioassayUploadPictureRequest;
import com.jiaye.cashloan.http.data.car.SaveValuationRequest;
import com.jiaye.cashloan.http.data.certification.Recommend;
import com.jiaye.cashloan.http.data.certification.RecommendRequest;
import com.jiaye.cashloan.http.data.certification.Step;
import com.jiaye.cashloan.http.data.certification.StepRequest;
import com.jiaye.cashloan.http.data.certification.UpdateStepRequest;
import com.jiaye.cashloan.http.data.dictionary.DictionaryList;
import com.jiaye.cashloan.http.data.dictionary.DictionaryListRequest;
import com.jiaye.cashloan.http.data.file.SubmitUploadFileRequest;
import com.jiaye.cashloan.http.data.file.UploadFileRequest;
import com.jiaye.cashloan.http.data.forgetpassword.ForgetPasswordCodeRequest;
import com.jiaye.cashloan.http.data.forgetpassword.ForgetPasswordRequest;
import com.jiaye.cashloan.http.data.home.CheckCompany;
import com.jiaye.cashloan.http.data.home.CheckCompanyRequest;
import com.jiaye.cashloan.http.data.home.CheckLoanRequest;
import com.jiaye.cashloan.http.data.home.LoanRequest;
import com.jiaye.cashloan.http.data.id.IDRequest;
import com.jiaye.cashloan.http.data.id.IDUploadPictureRequest;
import com.jiaye.cashloan.http.data.insurance.InsuranceRequest;
import com.jiaye.cashloan.http.data.loan.Contact;
import com.jiaye.cashloan.http.data.loan.ContactRequest;
import com.jiaye.cashloan.http.data.loan.ContractList;
import com.jiaye.cashloan.http.data.loan.ContractListRequest;
import com.jiaye.cashloan.http.data.loan.FileState;
import com.jiaye.cashloan.http.data.loan.FileStateRequest;
import com.jiaye.cashloan.http.data.loan.Loan;
import com.jiaye.cashloan.http.data.loan.LoanApply;
import com.jiaye.cashloan.http.data.loan.LoanApplyRequest;
import com.jiaye.cashloan.http.data.loan.LoanConfirm;
import com.jiaye.cashloan.http.data.loan.LoanConfirmInfo;
import com.jiaye.cashloan.http.data.loan.LoanConfirmInfoRequest;
import com.jiaye.cashloan.http.data.loan.LoanConfirmRequest;
import com.jiaye.cashloan.http.data.loan.LoanInfoAuth;
import com.jiaye.cashloan.http.data.loan.LoanInfoAuthRequest;
import com.jiaye.cashloan.http.data.loan.LoanProgress;
import com.jiaye.cashloan.http.data.loan.LoanProgressRequest;
import com.jiaye.cashloan.http.data.loan.Person;
import com.jiaye.cashloan.http.data.loan.PersonRequest;
import com.jiaye.cashloan.http.data.loan.QueryUploadPhoto;
import com.jiaye.cashloan.http.data.loan.QueryUploadPhotoRequest;
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
import com.jiaye.cashloan.http.data.loan.UploadLocation;
import com.jiaye.cashloan.http.data.loan.UploadLocationRequest;
import com.jiaye.cashloan.http.data.loan.UploadPhoto;
import com.jiaye.cashloan.http.data.loan.UploadPhotoRequest;
import com.jiaye.cashloan.http.data.loan.UploadRiskAppListRequest;
import com.jiaye.cashloan.http.data.loan.Visa;
import com.jiaye.cashloan.http.data.loan.VisaRequest;
import com.jiaye.cashloan.http.data.login.Login;
import com.jiaye.cashloan.http.data.login.LoginNormalRequest;
import com.jiaye.cashloan.http.data.login.LoginShortcutRequest;
import com.jiaye.cashloan.http.data.login.VerificationCodeRequest;
import com.jiaye.cashloan.http.data.my.CheckAccount;
import com.jiaye.cashloan.http.data.my.CheckAccountRequest;
import com.jiaye.cashloan.http.data.my.IDCardAuth;
import com.jiaye.cashloan.http.data.my.IDCardAuthRequest;
import com.jiaye.cashloan.http.data.my.Phone;
import com.jiaye.cashloan.http.data.my.PhoneRequest;
import com.jiaye.cashloan.http.data.phone.UpdatePhoneRequest;
import com.jiaye.cashloan.http.data.plan.Plan;
import com.jiaye.cashloan.http.data.plan.PlanRequest;
import com.jiaye.cashloan.http.data.search.Salesman;
import com.jiaye.cashloan.http.data.search.SalesmanRequest;
import com.jiaye.cashloan.http.data.search.SaveSalesman;
import com.jiaye.cashloan.http.data.search.SaveSalesmanRequest;
import com.jiaye.cashloan.http.data.sms.UploadSmsRequest;
import com.jiaye.cashloan.http.data.step3.Step3;
import com.jiaye.cashloan.http.data.step3.Step3Request;
import com.jiaye.cashloan.http.data.step4.Step4;
import com.jiaye.cashloan.http.data.step4.Step4Request;
import com.jiaye.cashloan.http.data.taobao.UpdateTaoBaoRequest;
import com.jiaye.cashloan.http.data.vehcile.CarPapersState;
import com.jiaye.cashloan.http.data.vehcile.CarPapersStateRequest;
import com.jiaye.cashloan.http.data.vehcile.UploadCarPapersRequest;
import com.jiaye.cashloan.http.data.vehcile.UploadFaceRequest;
import com.jiaye.cashloan.http.register.RegisterRequest;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * SatcatcheService
 *
 * @author 贾博瑄
 */
public interface SatcatcheService {

    /*新的接口*/

    /**
     * 快捷登录 1
     */
    @POST("carLogin")
    Flowable<SatcatcheResponse<Login>> loginShortcut(@Body SatcatcheRequest<LoginShortcutRequest> request);

    /**
     * 验证码 2
     */
    @POST("carSendMsg")
    Flowable<SatcatcheResponse<EmptyResponse>> verificationCode(@Body SatcatcheRequest<VerificationCodeRequest> request);

    /**
     * 是否需要选择分公司 3
     */
    @POST("queryUserSourceStatus")
    Flowable<SatcatcheResponse<CheckCompany>> checkCompany(@Body SatcatcheRequest<CheckCompanyRequest> request);

    /**
     * 保存销售人员 4
     */
    @POST("saveUserSource")
    Flowable<SatcatcheResponse<SaveSalesman>> saveSalesman(@Body SatcatcheRequest<SaveSalesmanRequest> request);

    /**
     * 查询销售人员数据 5
     */
    @POST("querySourceData")
    Flowable<SatcatcheResponse<Salesman>> salesman(@Body SatcatcheRequest<SalesmanRequest> request);

    /**
     * 查询认证到第几步 6
     */
    @POST("queryOrderStatus")
    Flowable<SatcatcheResponse<Step>> step(@Body SatcatcheRequest<StepRequest> request);

    /**
     * 修改认证到第几步 8
     */
    @POST("updateOrderStatus")
    Flowable<SatcatcheResponse<EmptyResponse>> updateStep(@Body SatcatcheRequest<UpdateStepRequest> request);

    /**
     * 查询认证第三步的具体状态 9
     */
    @POST("querySecAuthStatus")
    Flowable<SatcatcheResponse<Step3>> step3(@Body SatcatcheRequest<Step3Request> request);

    /**
     * 查询推荐人 10
     */
    @POST("queryUserSourceData")
    Flowable<SatcatcheResponse<Recommend>> recommend(@Body SatcatcheRequest<RecommendRequest> request);

    /**
     * 保存身份证认证 11
     */
    @POST("identityAuth/save")
    Flowable<SatcatcheResponse<EmptyResponse>> loanIDCardAuth(@Body SatcatcheRequest<IDRequest> request);

    /**
     * 保存活体检测 12
     */
    @POST("portraitComparison/save")
    Flowable<SatcatcheResponse<Bioassay>> loanFaceAuth(@Body SatcatcheRequest<BioassayRequest> request);

    /**
     * 保存个人信息 13
     */
    @POST("personInfo/save")
    Flowable<SatcatcheResponse<SavePerson>> savePerson(@Body SatcatcheRequest<SavePersonRequest> request);

    /**
     * 保存联系人信息 14
     */
    @POST("linkmansInfo/save")
    Flowable<SatcatcheResponse<SaveContact>> saveContact(@Body SatcatcheRequest<SaveContactRequest> request);

    /**
     * 保存手机信息 15
     */
    @POST("phoneOperator/save")
    Flowable<SatcatcheResponse<SavePhone>> savePhone(@Body SatcatcheRequest<SavePhoneRequest> request);

    /**
     * 上传车辆认证图片 16
     */
    @POST("carPapers/save")
    Flowable<SatcatcheResponse<EmptyResponse>> uploadCarPapers(@Body SatcatcheRequest<UploadCarPapersRequest> request);

    /**
     * 提交车辆认证状态 17
     */
    @POST("carPapers/state")
    Flowable<SatcatcheResponse<CarPapersState>> carPapersState(@Body SatcatcheRequest<CarPapersStateRequest> request);

    /**
     * 保存淘宝信息 18
     */
    @POST("taoBaoZhiFuBao/save")
    Flowable<SatcatcheResponse<SaveTaoBao>> saveTaoBao(@Body SatcatcheRequest<SaveTaoBaoRequest> request);

    /**
     * 上传认证材料 19
     */
    @POST("intoInfo/save")
    Flowable<SatcatcheResponse<EmptyResponse>> uploadFile(@Body SatcatcheRequest<UploadFileRequest> request);

    /**
     * 提交认证材料 20
     */
    @POST("intoInfo/state")
    Flowable<SatcatcheResponse<EmptyResponse>> submitUploadFile(@Body SatcatcheRequest<SubmitUploadFileRequest> request);

    /**
     * 车辆审批结果查询 21
     */
/*    @POST("queryExamineResult")
    Flowable<SatcatcheResponse<Step2>> step2(@Body SatcatcheRequest<Step2Request> request);*/

    /**
     * 是否可以进入存管账户 22
     */
    @POST("queryAccountStatus")
    Flowable<SatcatcheResponse<CheckAccount>> checkAccount(@Body SatcatcheRequest<CheckAccountRequest> request);

    /**
     * 上传人像照片 23
     */
    @POST("saveAlbumUrl")
    Flowable<SatcatcheResponse<EmptyResponse>> uploadFace(@Body SatcatcheRequest<UploadFaceRequest> request);

    /**
     * 绑定银行卡信息保存 24
     */
    @POST("saveBindingBankCard")
    Flowable<SatcatcheResponse<EmptyResponse>> saveBankCard(@Body SatcatcheRequest<SaveBankCardRequest> request);

    /**
     * 查询还款计划 25
     */
    @POST("queryGetRepaymentDetail")
    Flowable<SatcatcheResponse<Plan>> plan(@Body SatcatcheRequest<PlanRequest> request);

    /**
     * 查询批核金额 26
     */
    @POST("queryAmountMoney")
    Flowable<SatcatcheResponse<Step4>> step4(@Body SatcatcheRequest<Step4Request> request);

    /**
     * 注册 27
     */
    @POST("register")
    Flowable<SatcatcheResponse<Login>> register(@Body SatcatcheRequest<RegisterRequest> request);

    /**
     * 账号登录 28
     */
    @POST("loginPass")
    Flowable<SatcatcheResponse<Login>> loginNormal(@Body SatcatcheRequest<LoginNormalRequest> request);

    /**
     * 注册/忘记密码获取验证码 29
     */
    @POST("sendMsg")
    Flowable<SatcatcheResponse<EmptyResponse>> forgetPasswordCode(@Body SatcatcheRequest<ForgetPasswordCodeRequest> request);

    /**
     * 忘记密码 30
     */
    @POST("forgetPass")
    Flowable<SatcatcheResponse<EmptyResponse>> forgetPassword(@Body SatcatcheRequest<ForgetPasswordRequest> request);

    /**
     * 运营商认证 31
     */
    @POST("updateOperatorStatus")
    Flowable<SatcatcheResponse<EmptyResponse>> updatePhone(@Body SatcatcheRequest<UpdatePhoneRequest> request);

    /**
     * 淘宝认证 32
     */
    @POST("updateTaobaoStatus")
    Flowable<SatcatcheResponse<EmptyResponse>> updateTaoBao(@Body SatcatcheRequest<UpdateTaoBaoRequest> request);

    /**
     * 车险认证 33
     */
    @POST("updateCarInsuranceStatus")
    Flowable<SatcatcheResponse<EmptyResponse>> updateInsurance(@Body SatcatcheRequest<InsuranceRequest> request);

    /*旧的接口*/

    /**
     * 获取通用字典
     */
    @POST("dictEntry")
    Flowable<SatcatcheResponse<DictionaryList>> dictionaryList(@Body SatcatcheRequest<DictionaryListRequest> request);

    /**
     * 是否可以借款
     */
    @POST("userApprove/ifAgainLend")
    Flowable<SatcatcheResponse<EmptyResponse>> checkLoan(@Body SatcatcheRequest<CheckLoanRequest> request);

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
    Flowable<SatcatcheResponse<EmptyResponse>> uploadRiskAppList(@Body SatcatcheRequest<UploadRiskAppListRequest> request);

    /**
     * 上传人脸识别图片url
     */
    @POST("saveBioassayUrl")
    Flowable<SatcatcheResponse<EmptyResponse>> uploadBioassay(@Body SatcatcheRequest<BioassayUploadPictureRequest> request);

    /**
     * 身份证认证
     */
    @POST("cardAuth/queryIdentityInfo")
    Flowable<SatcatcheResponse<IDCardAuth>> idCardAuth(@Body SatcatcheRequest<IDCardAuthRequest> request);

    /**
     * 上传身份证认证图片url
     */
    @POST("saveIdcardUrl")
    Flowable<SatcatcheResponse<EmptyResponse>> uploadIdCard(@Body SatcatcheRequest<IDUploadPictureRequest> request);

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
     * 联系人信息
     */
    @POST("linkman/queryLinkInfo")
    Flowable<SatcatcheResponse<Contact>> contact(@Body SatcatcheRequest<ContactRequest> request);

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
     * 查看合同列表
     */
    @POST("shCompactShow")
    Flowable<SatcatcheResponse<ContractList>> contractList(@Body SatcatcheRequest<ContractListRequest> request);

    /**
     * 认证材料查询
     */
    @POST("searchMaterialPic")
    Flowable<SatcatcheResponse<FileState>> fileState(@Body SatcatcheRequest<FileStateRequest> request);

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

    /**
     * 上传相册查询接口
     */
    @POST("searchAlbumPic")
    Flowable<SatcatcheResponse<QueryUploadPhoto>> queryUploadPhoto(@Body SatcatcheRequest<QueryUploadPhotoRequest> request);

    /**
     * 上传图片
     */
    @POST("uploadAlbumPic")
    Flowable<SatcatcheResponse<UploadPhoto>> uploadPhoto(@Body SatcatcheRequest<UploadPhotoRequest> request);

    /**
     * 上传短信 26
     */
    @POST("saveSmsInfo")
    Flowable<SatcatcheResponse<EmptyResponse>> uploadSms(@Body SatcatcheRequest<UploadSmsRequest> request);

    /**
     * 上传车辆估值
     */
    @POST("saveValuation")
    Flowable<SatcatcheResponse<EmptyResponse>> saveCarValuation(@Body SatcatcheRequest<SaveValuationRequest> request);
}
