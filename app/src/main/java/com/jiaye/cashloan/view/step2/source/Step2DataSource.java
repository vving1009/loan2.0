package com.jiaye.cashloan.view.step2.source;

import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.certification.Step;
import com.jiaye.cashloan.http.data.step2.Step2;

import io.reactivex.Flowable;

/**
 * Step2DataSource
 *
 * @author 贾博瑄
 */

public interface Step2DataSource {

    Flowable<Step> requestStep();

    Flowable<Step2> requestStep2();

    Flowable<EmptyResponse> requestUpdateStep();
}
