package com.jiaye.cashloan.view.step3.input.source;

import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.step3.Step3;

import io.reactivex.Flowable;

/**
 * Step3InputDataSource
 *
 * @author 贾博瑄
 */

public interface Step3InputDataSource {

    Flowable<Step3> requestStep3();

    Flowable<EmptyResponse> requestUpdateStep();
}
