package com.jiaye.cashloan.view.step2;

import com.jiaye.cashloan.view.BaseViewContract;
import com.jiaye.cashloan.view.BasePresenter;

/**
 * Step2Contract
 *
 * @author 贾博瑄
 */

public interface Step2Contract {

    interface View extends BaseViewContract {

        void setText(String msg);

        void sendBroadcast();
    }

    interface Presenter extends BasePresenter {

        void requestStep();

        void onClickNext();
    }
}
