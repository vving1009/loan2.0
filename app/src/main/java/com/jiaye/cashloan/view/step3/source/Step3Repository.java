package com.jiaye.cashloan.view.step3.source;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.amount.AmountMoney;
import com.jiaye.cashloan.http.data.amount.AmountMoneyRequest;
import com.jiaye.cashloan.http.data.certification.Step;
import com.jiaye.cashloan.http.data.certification.StepRequest;
import com.jiaye.cashloan.http.data.certification.UpdateStepRequest;
import com.jiaye.cashloan.http.data.my.CreditInfo;
import com.jiaye.cashloan.http.data.my.CreditInfoRequest;
import com.jiaye.cashloan.http.data.search.Salesman;
import com.jiaye.cashloan.http.data.search.SalesmanRequest;
import com.jiaye.cashloan.http.data.search.SaveSalesmanRequest;
import com.jiaye.cashloan.http.utils.ResponseTransformer;
import com.jiaye.cashloan.http.utils.SatcatcheResponseTransformer;

import io.reactivex.Flowable;

/**
 * Step3Repository
 *
 * @author 贾博瑄
 */

public class Step3Repository implements Step3DataSource {

    @Override
    public Flowable<EmptyResponse> requestUpdateStep() {
        return Flowable.just(LoanApplication.getInstance().getDbHelper().queryUser())
                .map(user -> {
                    UpdateStepRequest request = new UpdateStepRequest();
                    request.setLoanId(user.getLoanId());
                    request.setStatus(6);
                    request.setMsg("到店借款");
                    return request;
                })
                .compose(new SatcatcheResponseTransformer<UpdateStepRequest, EmptyResponse>("updateStep"));
    }

    @Override
    public Flowable<Salesman> salesman() {
        return Flowable.just(new SalesmanRequest())
                .compose(new SatcatcheResponseTransformer<SalesmanRequest, Salesman>("salesman"))
                .map(search -> {
                    LoanApplication.getInstance().getDbHelper().deleteSales();
                    LoanApplication.getInstance().getDbHelper().insertSales(search.getList());
                    return search;
                });
    }

    @Override
    public Flowable<EmptyResponse> saveSalesman(SaveSalesmanRequest request) {
        return Flowable.just(request)
                .doOnNext(request1 -> {
                    String loadId = LoanApplication.getInstance().getDbHelper().queryUser().getLoanId();
                    request1.setLoanId(loadId);
                })
                .compose(new SatcatcheResponseTransformer<SaveSalesmanRequest, EmptyResponse>
                        ("saveSalesman"));
    }

    @Override
    public Flowable<Step> requestStep() {
        return Flowable.just(LoanApplication.getInstance().getDbHelper().queryUser())
                .map(user -> {
                    StepRequest request = new StepRequest();
                    request.setLoanId(user.getLoanId());
                    return request;
                })
                .compose(new SatcatcheResponseTransformer<StepRequest, Step>("step"));
    }

    @Override
    public Flowable<AmountMoney> requestAmountMoney() {
        return Flowable.just(LoanApplication.getInstance().getDbHelper().queryUser())
                .map(user -> {
                    AmountMoneyRequest request = new AmountMoneyRequest();
                    request.setLoanId(user.getLoanId());
                    return request;
                })
                .compose(new SatcatcheResponseTransformer<AmountMoneyRequest, AmountMoney>("queryAmountMoney"));
    }

    /**
     * 查询是否开户
     * @return
     */
    @Override
    public Flowable<CreditInfo> creditInfo() {
        return Flowable.just(new CreditInfoRequest())
                .compose(new ResponseTransformer<CreditInfoRequest, CreditInfo>("creditInfo"));
    }
}
