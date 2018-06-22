package com.jiaye.cashloan.view.step4;

import com.jiaye.cashloan.view.BaseViewContract;
import com.jiaye.cashloan.view.BasePresenter;

/**
 * Step4Contract
 *
 * @author 贾博瑄
 */

public interface Step4Contract {

    interface View extends BaseViewContract {

        void setText(String msg);

        void setLayoutVisibility();

        void setBtnVisibility(boolean visibility);

        void sendBroadcast();

        void finish();
    }

    interface Presenter extends BasePresenter {

        void requestStep();

        void onClickNext();
    }
}
