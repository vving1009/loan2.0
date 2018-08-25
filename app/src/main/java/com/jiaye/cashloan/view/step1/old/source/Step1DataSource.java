package com.jiaye.cashloan.view.step1.old.source;

import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.step2.Step2Input;

import io.reactivex.Flowable;

/**
 * Step2InputDataSource
 *
 * @author 贾博瑄
 */

public interface Step1DataSource {

    Flowable<Step2Input> requestStep1();

    Flowable<EmptyResponse> requestUpdateStep();
}
