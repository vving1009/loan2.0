package com.jiaye.cashloan.view.loancredit.step1.source;

import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.car.CarPrice;

import io.reactivex.Flowable;

/**
 * Step1DataSource
 *
 * @author 贾博瑄
 */

public interface Step1DataSource {

    Flowable<EmptyResponse> requestUpdateStep();
}
