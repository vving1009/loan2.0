package com.jiaye.cashloan.view.view.loan;

import com.jiaye.cashloan.http.data.loan.LoanDetails;
import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

import java.util.List;

/**
 * LoanDetailsContract
 *
 * @author 贾博瑄
 */

public interface LoanDetailsContract {

    interface View extends BaseViewContract {

        void setList(List<LoanDetails> list);
    }

    interface Presenter extends BasePresenter {

        void requestDetails();
    }
}
