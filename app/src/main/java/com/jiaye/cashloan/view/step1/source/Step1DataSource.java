package com.jiaye.cashloan.view.step1.source;

import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.car.CarPrice;

import io.reactivex.Flowable;

/**
 * Step1DataSource
 *
 * @author 贾博瑄
 */

public interface Step1DataSource {

    Flowable<CarPrice> requestCarPrice(Step1InputState step1);

    Flowable<EmptyResponse> saveCarPrice(String value);

    Flowable<EmptyResponse> requestUpdateStep();
}
