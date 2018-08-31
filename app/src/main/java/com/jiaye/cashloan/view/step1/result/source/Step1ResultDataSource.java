package com.jiaye.cashloan.view.step1.result.source;

import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.certification.Step;
import com.jiaye.cashloan.http.data.my.CreditInfo;
import com.jiaye.cashloan.http.data.step4.Step4;

import io.reactivex.Flowable;

/**
 * Step1ResultDataSource
 *
 * @author 贾博瑄
 */

public interface Step1ResultDataSource {

    Flowable<EmptyResponse> requestUpdateStep();
}
