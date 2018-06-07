package com.jiaye.cashloan.view.step1.source;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.data.certification.Step;
import com.jiaye.cashloan.http.data.certification.StepRequest;
import com.jiaye.cashloan.http.data.step1.Step1;
import com.jiaye.cashloan.http.data.step1.Step1Request;
import com.jiaye.cashloan.http.utils.SatcatcheResponseTransformer;

import io.reactivex.Flowable;

/**
 * Step1Repository
 *
 * @author 贾博瑄
 */

public class Step1Repository implements Step1DataSource {

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
    public Flowable<Step1> requestStep1() {
        return Flowable.just(LoanApplication.getInstance().getDbHelper().queryUser())
                .map(user -> {
                    Step1Request request = new Step1Request();
                    request.setLoanId(user.getLoanId());
                    return request;
                })
                .compose(new SatcatcheResponseTransformer<Step1Request, Step1>("step1"));
    }
}
