package com.jiaye.cashloan.view.data.loan.auth.source.info;

import com.jiaye.cashloan.http.data.loan.LoanInfoAuth;

import io.reactivex.Flowable;

/**
 * LoanAuthInfoDataSource
 *
 * @author 贾博瑄
 */

public interface LoanAuthInfoDataSource {

    /**
     * 请求借款个人资料状态
     */
    Flowable<LoanInfoAuth> requestLoanInfoAuth();
}
