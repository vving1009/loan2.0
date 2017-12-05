package com.jiaye.cashloan.view.data.loan.auth.source.sesame;

import com.jiaye.cashloan.http.base.Request;
import com.jiaye.cashloan.http.data.loan.SesameRequest;

import io.reactivex.Flowable;

/**
 * LoanAuthSesameDataSource
 *
 * @author 贾博瑄
 */

public interface LoanAuthSesameDataSource {

    Flowable<Request<SesameRequest>> sesame();
}
