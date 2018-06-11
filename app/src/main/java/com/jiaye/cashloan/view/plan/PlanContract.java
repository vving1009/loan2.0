package com.jiaye.cashloan.view.plan;

import com.jiaye.cashloan.http.data.loan.LoanPlan;
import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * PlanContract
 *
 * @author 贾博瑄
 */

public interface PlanContract {

    interface View extends BaseViewContract {

        void setPlans(LoanPlan.Plan[] plans);
    }

    interface Presenter extends BasePresenter {
    }
}
