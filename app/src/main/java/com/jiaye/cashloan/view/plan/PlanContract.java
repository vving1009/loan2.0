package com.jiaye.cashloan.view.plan;

import com.jiaye.cashloan.http.data.plan.Plan;
import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

import java.util.List;

/**
 * PlanContract
 *
 * @author 贾博瑄
 */

public interface PlanContract {

    interface View extends BaseViewContract {

        void setPlans(List<Plan.Details> list);
    }

    interface Presenter extends BasePresenter {
    }
}
