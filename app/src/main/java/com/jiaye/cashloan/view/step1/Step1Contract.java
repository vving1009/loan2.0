package com.jiaye.cashloan.view.step1;

import com.jiaye.cashloan.http.data.step1.Step1;
import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * Step1Contract
 *
 * @author 贾博瑄
 */

public interface Step1Contract {

    interface View extends BaseViewContract {

        void setStep1(Step1 step1);

        void showIDView();

        void showBioassayView();

        void showPersonalView();

        void showPhoneView();
    }

    interface Presenter extends BasePresenter {

        void requestStep();

        void onClickItem(int position);
    }
}
