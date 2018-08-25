package com.jiaye.cashloan.view.step1.input.source;

import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.step2.Step2Input;

import io.reactivex.Flowable;

/**
 * Step1InputDataSource
 *
 * @author 贾博瑄
 */

public interface Step1InputDataSource {

    Flowable<Step2Input> requestStep1();

    Flowable<EmptyResponse> requestUpdateStep();
}
