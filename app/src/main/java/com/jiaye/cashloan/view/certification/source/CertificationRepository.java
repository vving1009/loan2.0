package com.jiaye.cashloan.view.certification.source;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.data.certification.Recommend;
import com.jiaye.cashloan.http.data.certification.RecommendRequest;
import com.jiaye.cashloan.http.data.certification.Step;
import com.jiaye.cashloan.http.data.certification.StepRequest;
import com.jiaye.cashloan.http.utils.SatcatcheResponseTransformer;

import io.reactivex.Flowable;

/**
 * CertificationRepository
 *
 * @author 贾博瑄
 */

public class CertificationRepository implements CertificationDataSource {

    @Override
    public Flowable<Recommend> requestRecommend() {
        return Flowable.just(LoanApplication.getInstance().getDbHelper().queryUser())
                .map(user -> {
                    RecommendRequest request = new RecommendRequest();
                    request.setLoanId(user.getLoanId());
                    return request;
                })
                .compose(new SatcatcheResponseTransformer<RecommendRequest, Recommend>("recommend"));
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
}
