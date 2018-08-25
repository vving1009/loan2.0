package com.jiaye.cashloan.view.step4.source;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.certification.Step;
import com.jiaye.cashloan.http.data.certification.StepRequest;
import com.jiaye.cashloan.http.data.certification.UpdateStepRequest;
import com.jiaye.cashloan.http.data.my.CreditInfo;
import com.jiaye.cashloan.http.data.my.CreditInfoRequest;
import com.jiaye.cashloan.http.data.step4.Step4;
import com.jiaye.cashloan.http.data.step4.Step4Request;
import com.jiaye.cashloan.http.utils.ResponseTransformer;
import com.jiaye.cashloan.http.utils.SatcatcheResponseTransformer;

import io.reactivex.Flowable;

/**
 * Step1ResultRepository
 *
 * @author 贾博瑄
 */

public class Step4Repository implements Step4DataSource {

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
    public Flowable<Step4> requestStep4() {
        return Flowable.just(LoanApplication.getInstance().getDbHelper().queryUser())
                .map(user -> {
                    Step4Request request = new Step4Request();
                    request.setLoanId(user.getLoanId());
                    return request;
                })
                .compose(new SatcatcheResponseTransformer<Step4Request, Step4>("step4"));
    }

    @Override
    public Flowable<EmptyResponse> requestUpdateStep() {
        return Flowable.just(LoanApplication.getInstance().getDbHelper().queryUser())
                .map(user -> {
                    UpdateStepRequest request = new UpdateStepRequest();
                    request.setLoanId(user.getLoanId());
                    request.setStatus(10);
                    request.setMsg("确认借款");
                    return request;
                })
                .compose(new SatcatcheResponseTransformer<UpdateStepRequest, EmptyResponse>("updateStep"));
    }

    @Override
    public Flowable<CreditInfo> creditInfo() {
        return Flowable.just(new CreditInfoRequest())
                .compose(new ResponseTransformer<CreditInfoRequest, CreditInfo>("creditInfo"));
    }
}
