package com.jiaye.cashloan.view.certification.source;

import com.jiaye.cashloan.http.data.certification.Recommend;
import com.jiaye.cashloan.http.data.certification.Step;

import io.reactivex.Flowable;

/**
 * JdCarDataSource
 *
 * @author 贾博瑄
 */

public interface CertificationDataSource {

    Flowable<Recommend> requestRecommend();

    Flowable<Step> requestStep();
}
