package com.jiaye.cashloan.view.loancar.step4.source;

import com.jiaye.cashloan.http.data.loan.LoanDate;

import io.reactivex.Flowable;

/**
 * Step1ResultDataSource
 *
 * @author 贾博瑄
 */

public interface Step4DataSource {

    Flowable<LoanDate> requestLoanStatus();
}
