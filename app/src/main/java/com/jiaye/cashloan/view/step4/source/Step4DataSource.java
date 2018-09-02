package com.jiaye.cashloan.view.step4.source;

import com.jiaye.cashloan.http.data.certification.Step;

import io.reactivex.Flowable;

/**
 * Step1ResultDataSource
 *
 * @author 贾博瑄
 */

public interface Step4DataSource {

    Flowable<Step> requestStep();
}
