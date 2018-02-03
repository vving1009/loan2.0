package com.jiaye.cashloan.view.data.loan.source;

import com.jiaye.cashloan.http.data.loan.LoanPlan;

import io.reactivex.Flowable;

/**
 * LoanPlanDataSource
 *
 * @author 贾博瑄
 */

public interface LoanPlanDataSource {

    Flowable<LoanPlan.Plan[]> requestLoanPlan(String loanId);
}
