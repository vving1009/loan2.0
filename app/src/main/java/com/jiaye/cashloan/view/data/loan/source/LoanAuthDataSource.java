package com.jiaye.cashloan.view.data.loan.source;

import com.jiaye.cashloan.http.data.loan.LoanAuth;
import com.jiaye.cashloan.http.data.loan.UploadContactResponse;

import io.reactivex.Flowable;

/**
 * LoanAuthDataSource
 *
 * @author 贾博瑄
 */

public interface LoanAuthDataSource {

    /**
     * 上传联系人
     */
    Flowable<UploadContactResponse> uploadContact();

    /**
     * 请求借款认证信息并保存
     */
    Flowable<LoanAuth> requestLoanAuth();
}
