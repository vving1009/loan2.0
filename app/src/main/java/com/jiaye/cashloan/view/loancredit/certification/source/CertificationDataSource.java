package com.jiaye.cashloan.view.loancredit.certification.source;

import com.jiaye.cashloan.http.data.certification.Step;

import io.reactivex.Flowable;

/**
 * CertificationDataSource
 *
 * @author 贾博瑄
 */

public interface CertificationDataSource {

    Flowable<Step> requestStep();
}
