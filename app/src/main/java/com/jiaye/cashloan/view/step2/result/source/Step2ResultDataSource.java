package com.jiaye.cashloan.view.step2.result.source;

import com.jiaye.cashloan.http.base.EmptyResponse;

import io.reactivex.Flowable;

/**
 * Step3ResultDataSource
 *
 * @author 贾博瑄
 */

public interface Step2ResultDataSource {

    Flowable<EmptyResponse> requestUpdateStep();
}
