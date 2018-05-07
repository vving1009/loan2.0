package com.jiaye.cashloan.view.data.loan.auth.source.visa;

import com.jiaye.cashloan.http.base.Request;
import com.jiaye.cashloan.http.data.loan.LoanVisaRequest;
import com.jiaye.cashloan.http.data.loan.LoanVisaSMS;
import com.jiaye.cashloan.http.data.loan.Visa;

import io.reactivex.Flowable;

/**
 * LoanAuthVisaDataSource
 *
 * @author 贾博瑄
 */

public interface LoanAuthVisaDataSource {

    Flowable<Request<LoanVisaRequest>> visa(String type);

    Flowable<LoanVisaSMS> sendSMS(String type);

    Flowable<Visa> sign(String type, String sms);
}
