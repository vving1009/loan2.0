package com.jiaye.cashloan.view.certification.source;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.data.certification.Recommend;
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
        Recommend recommend = new Recommend();
        recommend.setCompany("天津");
        recommend.setNumber("000011");
        return Flowable.just(recommend);
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
