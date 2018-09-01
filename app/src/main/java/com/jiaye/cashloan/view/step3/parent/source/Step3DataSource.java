package com.jiaye.cashloan.view.step3.parent.source;

import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.step2.Step2;

import io.reactivex.Flowable;

/**
 * Step1InputDataSource
 *
 * @author 贾博瑄
 */

public interface Step3DataSource {

    Flowable<Step2> requestStep1();

    Flowable<EmptyResponse> requestUpdateStep();
}
