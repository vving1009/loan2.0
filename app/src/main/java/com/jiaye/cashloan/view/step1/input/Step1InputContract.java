package com.jiaye.cashloan.view.step1.input;

import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * Step1InputContract
 *
 * @author 贾博瑄
 */

public interface Step1InputContract {

    interface View extends BaseViewContract {

        void setStep1(Step1InputState step1);

        void sendBroadcast();

        void showCarModeView();

        void showCarDateView();

        void showCarMilesView();

        void showCarLocationView();

        void showResultView();
    }

    interface Presenter extends BasePresenter {

        void onClickItem(int position);

        void onClickNext();
    }
}
