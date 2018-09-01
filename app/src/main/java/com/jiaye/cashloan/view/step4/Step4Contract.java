package com.jiaye.cashloan.view.step4;

import android.support.annotation.DrawableRes;

import com.jiaye.cashloan.view.BaseViewContract;
import com.jiaye.cashloan.view.BasePresenter;

/**
 * Step1ResultContract
 *
 * @author 贾博瑄
 */

public interface Step4Contract {

    interface View extends BaseViewContract {

        void setWaitLoan();

        void setFinishLoan();

        void sendBroadcast();
    }

    interface Presenter extends BasePresenter {

        void requestStep();
    }
}
