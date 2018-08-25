package com.jiaye.cashloan.view.step2.result;

import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * Step3ResultContract
 *
 * @author 贾博瑄
 */

public interface Step2ResultContract {

    interface View extends BaseViewContract {

        void sendBroadcast();
    }

    interface Presenter extends BasePresenter {

        void onClickConfirm();
    }
}
