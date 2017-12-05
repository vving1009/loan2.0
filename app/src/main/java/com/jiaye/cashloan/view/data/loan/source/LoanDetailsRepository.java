package com.jiaye.cashloan.view.data.loan.source;

import com.jiaye.cashloan.http.data.loan.LoanDetails;

import io.reactivex.Flowable;

/**
 * LoanDetailsRepository
 *
 * @author 贾博瑄
 */

public class LoanDetailsRepository implements LoanDetailsDataSource {

    @Override
    public Flowable<LoanDetails> requestLoanDetails(String loanId) {
        return null;
    }
}
