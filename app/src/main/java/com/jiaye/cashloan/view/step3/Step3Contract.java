package com.jiaye.cashloan.view.step3;

import com.jiaye.cashloan.http.data.step3.Step3;
import com.jiaye.cashloan.persistence.Salesman;
import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * Step3Contract
 *
 * @author 贾博瑄
 */

public interface Step3Contract {

    interface View extends BaseViewContract {

        void showInputView();

        void showWaitView();

        void showRejectView();

        void showSuccessView(String value);

        void showMoreInfoView();

        void showOpenAccountView();

        void sendBroadcast();
    }

    interface Presenter extends BasePresenter {

        void requestNextStep();

        void requestStep();

        void uploadSelectSalesman(Salesman salesman);

        void onSuccessViewShown();
    }
}
