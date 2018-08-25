package com.jiaye.cashloan.view.step2.old;

import com.jiaye.cashloan.view.BaseViewContract;
import com.jiaye.cashloan.view.BasePresenter;

/**
 * Step2InputContract
 *
 * @author 贾博瑄
 */

public interface Step2Contract {

    interface View extends BaseViewContract {

        void setText(String msg);

        void sendBroadcast();

        void finish();
    }

    interface Presenter extends BasePresenter {

        void requestStep();

        void onClickNext();
    }
}
