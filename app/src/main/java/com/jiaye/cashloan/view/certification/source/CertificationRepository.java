package com.jiaye.cashloan.view.certification.source;

import com.jiaye.cashloan.http.data.certification.Recommend;
import com.jiaye.cashloan.http.data.certification.Step;

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
        Step step = new Step();
        step.setStep(0);
        return Flowable.just(step);
    }
}
