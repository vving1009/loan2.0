package com.jiaye.cashloan.view.step2.parent;

import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * Step1InputContract
 *
 * @author 贾博瑄
 */

public interface Step2Contract {

    interface View extends BaseViewContract {

        void showInputView();

        void showResultView(String value);
    }

    interface Presenter extends BasePresenter {
    }
}
