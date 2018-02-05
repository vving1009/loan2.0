package com.jiaye.cashloan.view.view.loan;

import com.jiaye.cashloan.http.data.loan.LoanApply;
import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * LoanDetailsContract
 *
 * @author 贾博瑄
 */

public interface LoanDetailsContract {

    interface View extends BaseViewContract {

        void setList(LoanApply.Card[] cards);
    }

    interface Presenter extends BasePresenter {

        void requestDetails(String state);
    }
}
