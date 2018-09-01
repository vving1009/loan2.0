package com.jiaye.cashloan.view.step3.input;

import com.jiaye.cashloan.persistence.Salesman;
import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * Step3InputContract
 *
 * @author 贾博瑄
 */

public interface Step3InputContract {

    interface View extends BaseViewContract {

        void showCompanyView();

        void showSalesmanView();

        void sendBroadcast();

        void showResultView();
    }

    interface Presenter extends BasePresenter {

        void requestStep();

        void onClickItem(int position);

        void onClickNext(Salesman salesman);
    }
}
