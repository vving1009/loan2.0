package com.jiaye.cashloan.view.data.loan.source;

import com.jiaye.cashloan.http.data.loan.LoanApply;

import io.reactivex.Flowable;

/**
 * LoanDetailsDataSource
 *
 * @author 贾博瑄
 */

public interface LoanDetailsDataSource {

    Flowable<LoanApply> requestLoanApply(String state);
}
