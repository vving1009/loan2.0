package com.jiaye.cashloan.view.loancar.step3.source;

import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.amount.AmountMoney;
import com.jiaye.cashloan.http.data.certification.Step;
import com.jiaye.cashloan.http.data.home.CheckCompany;
import com.jiaye.cashloan.http.data.loan.Visa;
import com.jiaye.cashloan.http.data.my.CreditInfo;
import com.jiaye.cashloan.http.data.search.Salesman;
import com.jiaye.cashloan.http.data.search.SaveSalesmanRequest;

import io.reactivex.Flowable;

/**
 * Step3DataSource
 *
 * @author 贾博瑄
 */

public interface Step3DataSource {

    Flowable<EmptyResponse> requestUpdateStep(int step, String msg);

    Flowable<Salesman> salesman();

    Flowable<EmptyResponse> saveSalesman(SaveSalesmanRequest request);

    Flowable<Step> requestStep();

    Flowable<AmountMoney> requestAmountMoney();

    Flowable<CreditInfo> creditInfo();

    Flowable<Visa> sign();

    Flowable<CheckCompany> checkCompany();
}
