package com.jiaye.cashloan.view.step2.source;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.car.QueryValuationRequest;
import com.jiaye.cashloan.http.data.car.QueryValuationResponse;
import com.jiaye.cashloan.http.data.certification.Step;
import com.jiaye.cashloan.http.data.certification.StepRequest;
import com.jiaye.cashloan.http.data.certification.UpdateStepRequest;
import com.jiaye.cashloan.http.data.step2.Step2;
import com.jiaye.cashloan.http.data.step2.Step2Request;
import com.jiaye.cashloan.http.utils.SatcatcheResponseTransformer;

import io.reactivex.Flowable;

/**
 * Step2Repository
 *
 * @author 贾博瑄
 */

public class Step2Repository implements Step2DataSource {


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
    public Flowable<Step2> requestStep2() {
        return Flowable.just(LoanApplication.getInstance().getDbHelper().queryUser())
                .map(user -> {
                    Step2Request request = new Step2Request();
                    request.setLoanId(user.getLoanId());
                    return request;
                })
                .compose(new SatcatcheResponseTransformer<Step2Request, Step2>("step2"));
    }

    @Override
    public Flowable<EmptyResponse> requestUpdateStep(int step, String msg) {
        return Flowable.just(LoanApplication.getInstance().getDbHelper().queryUser())
                .map(user -> {
                    UpdateStepRequest request = new UpdateStepRequest();
                    request.setLoanId(user.getLoanId());
                    request.setStatus(step);
                    request.setMsg(msg);
                    return request;
                })
                .compose(new SatcatcheResponseTransformer<UpdateStepRequest, EmptyResponse>("updateStep"));
    }

    @Override
    public Flowable<QueryValuationResponse> queryCarValuation() {
        return Flowable.just(LoanApplication.getInstance().getDbHelper().queryUser())
                .map(user -> {
                    QueryValuationRequest request = new QueryValuationRequest();
                    request.setJlaId(user.getLoanId());
                    return request;
                })
                .compose(new SatcatcheResponseTransformer<QueryValuationRequest, QueryValuationResponse>("queryCarValuation"));
    }
}
