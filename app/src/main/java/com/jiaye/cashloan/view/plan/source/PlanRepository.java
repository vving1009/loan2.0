package com.jiaye.cashloan.view.plan.source;

import com.jiaye.cashloan.http.data.plan.Plan;
import com.jiaye.cashloan.http.data.plan.PlanRequest;
import com.jiaye.cashloan.http.utils.SatcatcheResponseTransformer;

import java.util.List;

import io.reactivex.Flowable;

/**
 * PlanRepository
 *
 * @author 贾博瑄
 */

public class PlanRepository implements PlanDataSource {

    @Override
    public Flowable<List<Plan.Details>> plan() {
        return Flowable.just(new PlanRequest())
                .compose(new SatcatcheResponseTransformer<PlanRequest, Plan>("plan"))
                .map(Plan::getList);
    }
}
