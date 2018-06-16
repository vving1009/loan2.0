package com.jiaye.cashloan.view.plan.source;

import com.jiaye.cashloan.http.data.plan.Plan;

import java.util.List;

import io.reactivex.Flowable;

/**
 * PlanDataSource
 *
 * @author 贾博瑄
 */

public interface PlanDataSource {

    Flowable<List<Plan.Details>> plan();
}
