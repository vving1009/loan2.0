package com.jiaye.cashloan.view.data.loan.auth.source.sesame;

import com.jiaye.cashloan.http.data.loan.Sesame;

import io.reactivex.Flowable;

/**
 * LoanAuthSesameDataSource
 *
 * @author 贾博瑄
 */

public interface LoanAuthSesameDataSource {

    Flowable<Sesame> sesame();
}
