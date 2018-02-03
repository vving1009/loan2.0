package com.jiaye.cashloan.view.view.loan;

import com.jiaye.cashloan.http.data.loan.LoanPlan;
import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * LoanPlanContract
 *
 * @author 贾博瑄
 */

public interface LoanPlanContract {

    interface View extends BaseViewContract {

        void setPlans(LoanPlan.Plan[] plans);
    }

    interface Presenter extends BasePresenter {

        void requestLoanPlan(String loanId);
    }
}
