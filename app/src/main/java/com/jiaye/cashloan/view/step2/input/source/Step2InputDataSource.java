package com.jiaye.cashloan.view.step2.input.source;

import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.step2.Step2Input;

import io.reactivex.Flowable;

/**
 * Step2InputDataSource
 *
 * @author 贾博瑄
 */

public interface Step2InputDataSource {

    Flowable<Step2Input> requestStep2();

    Flowable<EmptyResponse> requestUpdateStep();
}
