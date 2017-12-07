package com.jiaye.cashloan.view.data.my.certificate.idcard.source;

import com.jiaye.cashloan.http.data.my.IDCardAuth;

import io.reactivex.Flowable;

/**
 * IdCardDataSource
 *
 * @author 贾博瑄
 */

public interface IdCardDataSource {

    Flowable<IDCardAuth> idCardAuth();
}
