package com.jiaye.cashloan.view.loancar.step4;

import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * Step1ResultContract
 *
 * @author 贾博瑄
 */

public interface Step4Contract {

    interface View extends BaseViewContract {

        void showWaitLoanView();

        void showFinishLoanView();
    }

    interface Presenter extends BasePresenter {

        void requestLoanStatus();
    }
}
