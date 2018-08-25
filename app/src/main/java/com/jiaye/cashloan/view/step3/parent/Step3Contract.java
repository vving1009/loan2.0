package com.jiaye.cashloan.view.step3.parent;

import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * Step1InputContract
 *
 * @author 贾博瑄
 */

public interface Step3Contract {

    interface View extends BaseViewContract {

        void showInputView();

        void showResultView();
    }

    interface Presenter extends BasePresenter {
    }
}
