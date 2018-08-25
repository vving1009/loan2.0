package com.jiaye.cashloan.view.step2.input;

import com.jiaye.cashloan.http.data.step2.Step2Input;
import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * Step2InputContract
 *
 * @author 贾博瑄
 */

public interface Step2InputContract {

    interface View extends BaseViewContract {

        void setStep1(Step2Input step2Input);

        void showInsuranceView();

        void showBioassayView();

        void showInfoView();

        void showPhoneView();

        void showTaobaoView();

        void showVehicleView();

        void sendBroadcast();
    }

    interface Presenter extends BasePresenter {

        void requestStep();

        void onClickItem(int position);

        void onClickNext();
    }
}
