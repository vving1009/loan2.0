package com.jiaye.cashloan.view.step3.source;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.certification.UpdateStepRequest;
import com.jiaye.cashloan.http.data.search.Salesman;
import com.jiaye.cashloan.http.data.search.SalesmanRequest;
import com.jiaye.cashloan.http.data.search.SaveSalesman;
import com.jiaye.cashloan.http.data.search.SaveSalesmanRequest;
import com.jiaye.cashloan.http.data.step3.Step3;
import com.jiaye.cashloan.http.data.step3.Step3Request;
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
    public Flowable<SaveSalesman> saveSalesman(SaveSalesmanRequest request) {
        return Flowable.just(request)
                .doOnNext(request1 -> {
                    String loadId = LoanApplication.getInstance().getDbHelper().queryUser().getLoanId();
                    request1.setLoanId(loadId);
                })
                .compose(new SatcatcheResponseTransformer<SaveSalesmanRequest, SaveSalesman>
                        ("saveSalesman"));
    }
}
