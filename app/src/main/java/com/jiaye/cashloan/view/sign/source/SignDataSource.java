package com.jiaye.cashloan.view.sign.source;

import com.jiaye.cashloan.http.base.Request;
import com.jiaye.cashloan.http.data.loan.LoanVisaRequest;
import com.jiaye.cashloan.http.data.loan.Visa;

import io.reactivex.Flowable;

/**
 * SignDataSource
 *
 * @author 贾博瑄
 */

public interface SignDataSource {

    Flowable<Request<LoanVisaRequest>> show();

    Flowable<Visa> sign();
}
