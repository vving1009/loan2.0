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

        void showWaitView();

        void ShowRejectView();

        void showSuccessView(String value);

        void sendBroadcast();
    }

    interface Presenter extends BasePresenter {

        void requestStep();

        void onClickNext(Salesman salesman);

        void requestUpdateStep();
    }
}
