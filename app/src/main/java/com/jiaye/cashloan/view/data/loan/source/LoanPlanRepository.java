package com.jiaye.cashloan.view.data.loan.source;

import com.jiaye.cashloan.http.data.loan.LoanPlan;
import com.jiaye.cashloan.http.data.loan.LoanPlanRequest;
import com.jiaye.cashloan.http.utils.SatcatcheResponseTransformer;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * LoanPlanRepository
 *
 * @author 贾博瑄
 */

public class LoanPlanRepository implements LoanPlanDataSource {

    @Override
    public Flowable<LoanPlan.Plan[]> requestLoanPlan(String loanId) {
        return Flowable.just(loanId)
                .map(new Function<String, LoanPlanRequest>() {
                    @Override
                    public LoanPlanRequest apply(String loanId) throws Exception {
                        LoanPlanRequest request = new LoanPlanRequest();
                        request.setLoanId(loanId);
                        return request;
                    }
                })
                .compose(new SatcatcheResponseTransformer<LoanPlanRequest, LoanPlan>("loanPlan"))
                .map(new Function<LoanPlan, LoanPlan.Plan[]>() {
                    @Override
                    public LoanPlan.Plan[] apply(LoanPlan loanPlan) throws Exception {
                        return loanPlan.getPlans();
                    }
                });
    }
}
