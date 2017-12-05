package com.jiaye.cashloan.view.data.loan.source;

import com.jiaye.cashloan.http.data.loan.LoanProgress;

import io.reactivex.Flowable;

/**
 * LoanProgressDataSource
 *
 * @author 贾博瑄
 */

public interface LoanProgressDataSource {

    Flowable<LoanProgress> requestLoanProgress(String loanId);
}
