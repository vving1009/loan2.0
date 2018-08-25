package com.jiaye.cashloan.view.step3.input;

import com.jiaye.cashloan.http.data.step3.Step3;
import com.jiaye.cashloan.view.BaseViewContract;
import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.FunctionActivity;

/**
 * Step3InputContract
 *
 * @author 贾博瑄
 */

public interface Step3InputContract {

    interface View extends BaseViewContract {

        void setStep3(Step3InputState step3);

        void showCompanyView();

        void showSalesmanView();

        void sendBroadcast();

        void showResultView();
    }

    interface Presenter extends BasePresenter {

        void requestStep();

        void onClickItem(int position);

        void onClickNext();
    }
}
