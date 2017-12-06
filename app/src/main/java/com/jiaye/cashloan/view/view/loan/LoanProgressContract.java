package com.jiaye.cashloan.view.view.loan;

import com.jiaye.cashloan.http.data.loan.LoanProgress;
import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

import java.util.List;

/**
 * LoanProgressContract
 *
 * @author 贾博瑄
 */

public interface LoanProgressContract {

    interface View extends BaseViewContract {

        void setList(List<LoanProgress.Data> list);
    }

    interface Presenter extends BasePresenter {

        void requestLoanProgress(String loanId);
    }
}
