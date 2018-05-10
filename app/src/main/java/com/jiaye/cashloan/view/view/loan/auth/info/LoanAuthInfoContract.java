package com.jiaye.cashloan.view.view.loan.auth.info;

import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * LoanAuthInfoContract
 *
 * @author 贾博瑄
 */

public interface LoanAuthInfoContract {

    interface View extends BaseViewContract {

        void setPerson(String text);

        void setContact(String text);
    }

    interface Presenter extends BasePresenter {
    }
}
