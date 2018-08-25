package com.jiaye.cashloan.view.step4;

import com.jiaye.cashloan.view.BaseViewContract;
import com.jiaye.cashloan.view.BasePresenter;

/**
 * Step1ResultContract
 *
 * @author 贾博瑄
 */

public interface Step4Contract {

    interface View extends BaseViewContract {

        void setText(String msg);

        void setLayoutVisibility();

        void setBtnTextById(int resId);

        void sendBroadcast();

        void showBindBankView();
    }

    interface Presenter extends BasePresenter {

        void requestStep();

        void onClickConfirm();

        void onClickOpen();
    }
}
