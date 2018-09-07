package com.jiaye.cashloan.view.loancar.step1;

import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;
import com.jiaye.cashloan.view.loancar.step1.source.Step1InputState;

/**
 * Step1Contract
 *
 * @author 贾博瑄
 */

public interface Step1Contract {

    interface View extends BaseViewContract {

        void showResultView(String min, String max);

        void showCarBrandView();

        void showCarDateView();

        void showCarMilesView();

        void showCarLocationView();

        void sendBroadcast();
    }

    interface Presenter extends BasePresenter {

        void onClickItem(int position);

        void onClickNext();

        void saveCarPrice();

        Step1InputState getStep1();
    }
}
