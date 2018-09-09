package com.jiaye.cashloan.view.loancredit.startpage.source;

import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.loan.Loan;
import com.jiaye.cashloan.persistence.User;

import io.reactivex.Flowable;

/**
 * StartPageDataSource
 *
 * @author 贾博瑄
 */

public interface StartPageDataSource {

    Flowable<User> queryUser();

    Flowable<EmptyResponse> checkLoan();

    Flowable<Loan> requestLoan();

    Flowable<EmptyResponse> uploadRiskAppList();
}
