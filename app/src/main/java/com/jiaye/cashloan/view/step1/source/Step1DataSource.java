package com.jiaye.cashloan.view.step1.source;

import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.certification.Step;
import com.jiaye.cashloan.http.data.step1.Step1;

import io.reactivex.Flowable;

/**
 * Step1DataSource
 *
 * @author 贾博瑄
 */

public interface Step1DataSource {

    Flowable<Step> requestStep();

    Flowable<Step1> requestStep1();

    Flowable<EmptyResponse> requestUpdateStep();
}
