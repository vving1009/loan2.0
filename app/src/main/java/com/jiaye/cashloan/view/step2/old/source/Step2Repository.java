package com.jiaye.cashloan.view.step2.old.source;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.certification.Step;
import com.jiaye.cashloan.http.data.certification.StepRequest;
import com.jiaye.cashloan.http.data.certification.UpdateStepRequest;
import com.jiaye.cashloan.http.data.step2.Step2Result;
import com.jiaye.cashloan.http.data.step2.Step2ResultRequest;
import com.jiaye.cashloan.http.utils.SatcatcheResponseTransformer;

import io.reactivex.Flowable;

/**
 * Step2InputRepository
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
    public Flowable<Step2Result> requestStep2() {
        return Flowable.just(LoanApplication.getInstance().getDbHelper().queryUser())
                .map(user -> {
                    Step2ResultRequest request = new Step2ResultRequest();
                    request.setLoanId(user.getLoanId());
                    return request;
                })
                .compose(new SatcatcheResponseTransformer<Step2ResultRequest, Step2Result>("step2"));
    }

    @Override
    public Flowable<EmptyResponse> requestUpdateStep() {
        return Flowable.just(LoanApplication.getInstance().getDbHelper().queryUser())
                .map(user -> {
                    UpdateStepRequest request = new UpdateStepRequest();
                    request.setLoanId(user.getLoanId());
                    request.setStatus(5);
                    request.setMsg("提交信息");
                    return request;
                })
                .compose(new SatcatcheResponseTransformer<UpdateStepRequest, EmptyResponse>("updateStep"));
    }
}
