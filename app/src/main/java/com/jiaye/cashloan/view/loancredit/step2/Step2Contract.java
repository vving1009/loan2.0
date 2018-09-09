package com.jiaye.cashloan.view.loancredit.step2;

import com.jiaye.cashloan.http.data.step2.Step2;
import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * Step2Contract
 *
 * @author 贾博瑄
 */

public interface Step2Contract {

    interface View extends BaseViewContract {

        void sendBroadcast();

        void setStep2(Step2 step2);

        void showIDView();

        void showBioassayView();

        void showPhoneView();

        void showTaobaoView();
    }

    interface Presenter extends BasePresenter {
        void requestStep();

        void onClickItem(int position);

        void onClickNext();

        void requestUpdateStep();
    }
}
