package com.jiaye.cashloan.view.step2.old.source;

import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.certification.Step;
import com.jiaye.cashloan.http.data.step2.Step2Result;

import io.reactivex.Flowable;

/**
 * Step2InputDataSource
 *
 * @author 贾博瑄
 */

public interface Step2DataSource {

    Flowable<Step> requestStep();

    Flowable<Step2Result> requestStep2();

    Flowable<EmptyResponse> requestUpdateStep();
}
