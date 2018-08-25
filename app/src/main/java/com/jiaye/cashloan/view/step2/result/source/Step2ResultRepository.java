package com.jiaye.cashloan.view.step2.result.source;

import com.jiaye.cashloan.http.base.EmptyResponse;

import io.reactivex.Flowable;

/**
 * Step3ResultRepository
 *
 * @author 贾博瑄
 */

public class Step2ResultRepository implements Step2ResultDataSource {

    @Override
    public Flowable<EmptyResponse> requestUpdateStep() {
        /*return Flowable.just(LoanApplication.getInstance().getDbHelper().queryUser())
                .map(user -> {
                    UpdateStepRequest request = new UpdateStepRequest();
                    request.setLoanId(user.getLoanId());
                    request.setStatus(10);
                    request.setMsg("确认借款");
                    return request;
                })
                .compose(new SatcatcheResponseTransformer<UpdateStepRequest, EmptyResponse>("updateStep"));*/
        return Flowable.just(new EmptyResponse());
    }
}
