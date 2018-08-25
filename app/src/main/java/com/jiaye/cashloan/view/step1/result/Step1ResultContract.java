package com.jiaye.cashloan.view.step1.result;

import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * Step1ResultContract
 *
 * @author 贾博瑄
 */

public interface Step1ResultContract {

    interface View extends BaseViewContract {

        void sendBroadcast();
    }

    interface Presenter extends BasePresenter {

        void onClickConfirm();
    }
}
