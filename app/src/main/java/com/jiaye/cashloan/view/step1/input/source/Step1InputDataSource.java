package com.jiaye.cashloan.view.step1.input.source;

import com.jiaye.cashloan.http.data.car.CarPrice;
import com.jiaye.cashloan.view.step1.input.Step1InputState;

import io.reactivex.Flowable;

/**
 * Step1InputDataSource
 *
 * @author 贾博瑄
 */

public interface Step1InputDataSource {

    //Flowable<Step2Input> requestStep1();

    Flowable<CarPrice> requestCarPrice(Step1InputState step1);
}
