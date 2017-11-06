package com.jiaye.cashloan.view.data.loan.source;

import com.jiaye.cashloan.http.data.loan.LoanAuth;

import io.reactivex.Flowable;

/**
 * LoanAuthDataSource
 *
 * @author 贾博瑄
 */

public interface LoanAuthDataSource {

    /**
     * 请求借款认证信息
     */
    Flowable<LoanAuth> requestLoanAuth();
}
