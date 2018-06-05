package com.jiaye.cashloan.view.step1.source;

import com.jiaye.cashloan.http.data.step1.Step1;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Step1DataSource
 *
 * @author 贾博瑄
 */

public interface Step1DataSource {

    Flowable<List<Step1>> requestStep1();
}
