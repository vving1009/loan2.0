package com.jiaye.cashloan.view.data.loan.source;

import com.jiaye.cashloan.http.data.loan.LoanConfirmInfo;

import io.reactivex.Flowable;

/**
 * LoanConfirmDataSource
 *
 * @author 贾博瑄
 */

public interface LoanConfirmDataSource {

    Flowable<LoanConfirmInfo> requestLoanConfirmInfo();

    Flowable<String> requestLoanConfirm();

    Flowable<String> queryLoanId();
}
