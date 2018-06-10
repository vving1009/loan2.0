package com.jiaye.cashloan.view.step3;

import com.jiaye.cashloan.http.data.step3.Step3;
import com.jiaye.cashloan.view.BaseViewContract;
import com.jiaye.cashloan.view.BasePresenter;

/**
 * Step3Contract
 *
 * @author 贾博瑄
 */

public interface Step3Contract {

    interface View extends BaseViewContract {

        void setStep3(Step3 step3);

        void showTaoBaoView();

        void showFileView();

        void showSignView();

        void sendBroadcast();
    }

    interface Presenter extends BasePresenter {

        void requestStep();

        void onClickItem(int position);

        void onClickNext();
    }
}
