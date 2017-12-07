package com.jiaye.cashloan.view.data.my.certificate.source;

import com.jiaye.cashloan.http.data.auth.Auth;

import io.reactivex.Flowable;

/**
 * CertificateDataSource
 *
 * @author 贾博瑄
 */

public interface CertificateDataSource {

    Flowable<Auth> requestAuth();

    Auth getAuth();
}
