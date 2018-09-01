package com.jiaye.cashloan.view.step2;

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

        void showResultView(String price);

        void sendBroadcast();

        void setStep2(Step2 step2);

        void showInsuranceView();

        void showBioassayView();

        void showInfoView();

        void showPhoneView();

        void showTaobaoView();

        void showVehicleView();
    }

    interface Presenter extends BasePresenter {

        void requestStep();

        void onClickItem(int position);

        void onClickNext();

        void requestUpdateStep();
    }
}
