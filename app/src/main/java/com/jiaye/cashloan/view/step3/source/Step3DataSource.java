package com.jiaye.cashloan.view.step3.source;

import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.search.Salesman;
import com.jiaye.cashloan.http.data.search.SaveSalesman;
import com.jiaye.cashloan.http.data.search.SaveSalesmanRequest;
import com.jiaye.cashloan.http.data.step3.Step3;

import io.reactivex.Flowable;

/**
 * Step3DataSource
 *
 * @author 贾博瑄
 */

public interface Step3DataSource {

    Flowable<EmptyResponse> requestUpdateStep();

    Flowable<Salesman> salesman();

    Flowable<SaveSalesman> saveSalesman(SaveSalesmanRequest request);
}
