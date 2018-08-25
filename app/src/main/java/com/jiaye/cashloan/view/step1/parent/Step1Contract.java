package com.jiaye.cashloan.view.step1.parent;

import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * Step1InputContract
 *
 * @author 贾博瑄
 */

public interface Step1Contract {

    interface View extends BaseViewContract {

        void showInputView();

        void showResultView(String... value);
    }

    interface Presenter extends BasePresenter {
    }
}
