package com.jiaye.cashloan.view.certification.source;

import com.jiaye.cashloan.http.data.certification.Step;
import com.jiaye.cashloan.http.data.my.CreditInfo;

import io.reactivex.Flowable;

/**
 * CertificationDataSource
 *
 * @author 贾博瑄
 */

public interface CertificationDataSource {

    Flowable<Step> requestStep();

    Flowable<CreditInfo> creditInfo();
}
