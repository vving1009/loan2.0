package com.jiaye.cashloan.http;

import com.jiaye.cashloan.http.base.Request;
import com.jiaye.cashloan.http.base.Response;
import com.jiaye.cashloan.http.data.dictionary.DictionaryListRequest;
import com.jiaye.cashloan.http.data.loan.LoanBindBank;
import com.jiaye.cashloan.http.data.loan.LoanBindBankRequest;
import com.jiaye.cashloan.http.data.loan.LoanOpenSMS;
import com.jiaye.cashloan.http.data.loan.LoanOpenSMSRequest;
import com.jiaye.cashloan.http.data.loan.LoanVisaSMS;
import com.jiaye.cashloan.http.data.loan.LoanVisaSMSRequest;
import com.jiaye.cashloan.http.data.loan.LoanVisaSign;
import com.jiaye.cashloan.http.data.loan.LoanVisaSignRequest;
import com.jiaye.cashloan.http.data.loan.SupportBankList;
import com.jiaye.cashloan.http.data.loan.SupportBankListRequest;
import com.jiaye.cashloan.http.data.loan.WatchContract;
import com.jiaye.cashloan.http.data.loan.WatchContractRequest;
import com.jiaye.cashloan.http.data.my.Bank;
import com.jiaye.cashloan.http.data.my.BankRequest;
import com.jiaye.cashloan.http.data.my.CreditBalance;
import com.jiaye.cashloan.http.data.my.CreditBalanceRequest;
import com.jiaye.cashloan.http.data.my.CreditInfo;
import com.jiaye.cashloan.http.data.my.CreditInfoRequest;
import com.jiaye.cashloan.http.data.my.CreditPasswordStatus;
import com.jiaye.cashloan.http.data.my.CreditPasswordStatusRequest;
import com.jiaye.cashloan.http.data.my.CreditUnBindBank;
import com.jiaye.cashloan.http.data.my.CreditUnBindBankRequest;

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
    Flowable<ResponseBody> dictCommon(@Body DictionaryListRequest request);

    /**
     * 电子签章短信验证码
     */
    @POST("esign/sendMobileCode")
    Flowable<Response<LoanVisaSMS>> loanVisaSMS(@Body Request<LoanVisaSMSRequest> request);

    /**
     * 电子签章
     */
    @POST("esign/eSignCompact")
    Flowable<Response<LoanVisaSign>> loanVisaSign(@Body Request<LoanVisaSignRequest> request);

    /**
     * 银行卡
     */
    @POST("bindBankCard/displayCardInfo")
    Flowable<Response<Bank>> bank(@Body Request<BankRequest> request);

    /**
     * 查看合同
     */
    @POST("shCompactDetail")
    Flowable<Response<WatchContract>> watchContract(@Body Request<WatchContractRequest> request);

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
     * 开户及密码状态查询
     */
    @POST("pwdSetQuery")
    Flowable<Response<CreditPasswordStatus>> creditPasswordStatus(@Body Request<CreditPasswordStatusRequest> request);

    /**
     * 开户信息查询
     */
    @POST("jxUtil")
    Flowable<Response<CreditInfo>> creditInfo(@Body Request<CreditInfoRequest> request);

    /**
     * 查询余额
     */
    @POST("balanceQuery")
    Flowable<Response<CreditBalance>> creditBalance(@Body Request<CreditBalanceRequest> request);

    /**
     * 绑定银行卡
     */
    @POST("bindBankCard")
    Flowable<Response<LoanBindBank>> loanBindBank(@Body Request<LoanBindBankRequest> request);

    /**
     * 解绑银行卡
     */
    @POST("unbind")
    Flowable<Response<CreditUnBindBank>> creditBankUnBind(@Body Request<CreditUnBindBankRequest> request);

    /**
     * 查看银行列表信息
     */
    @POST("bklist")
    Flowable<Response<SupportBankList>> supportBankList(@Body Request<SupportBankListRequest> request);
}
