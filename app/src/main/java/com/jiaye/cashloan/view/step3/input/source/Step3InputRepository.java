package com.jiaye.cashloan.view.step3.input.source;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.certification.UpdateStepRequest;
import com.jiaye.cashloan.http.data.step3.Step3;
import com.jiaye.cashloan.http.data.step3.Step3Request;
import com.jiaye.cashloan.http.utils.SatcatcheResponseTransformer;

import io.reactivex.Flowable;

/**
 * Step3InputRepository
 *
 * @author 贾博瑄
 */

public class Step3InputRepository implements Step3InputDataSource {

    @Override
    public Flowable<Step3> requestStep3() {
        return Flowable.just(LoanApplication.getInstance().getDbHelper().queryUser())
                .map(user -> {
                    Step3Request request = new Step3Request();
                    request.setLoanId(user.getLoanId());
                    return request;
                })
                .compose(new SatcatcheResponseTransformer<Step3Request, Step3>("step3"));
    }

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
}
