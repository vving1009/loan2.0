package com.jiaye.cashloan.view.data.loan.source;

import com.jiaye.cashloan.http.data.loan.LoanDetails;

import io.reactivex.Flowable;

/**
 * LoanDetailsDataSource
 *
 * @author 贾博瑄
 */

public interface LoanDetailsDataSource {

    Flowable<LoanDetails> requestLoanDetails();
}
