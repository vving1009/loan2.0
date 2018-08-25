package com.jiaye.cashloan.view.step3.result;

import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;
import com.jiaye.cashloan.view.FunctionActivity;

/**
 * Step3ResultContract
 *
 * @author 贾博瑄
 */

public interface Step3ResultContract {

    interface View extends BaseViewContract {

        void sendBroadcast();

        void showApprovingView();

        void showRejectView();

        void showAmountView(String value);
    }

    interface Presenter extends BasePresenter {

        void onClickConfirm();
    }
}
