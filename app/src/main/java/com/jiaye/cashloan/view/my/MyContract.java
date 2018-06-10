package com.jiaye.cashloan.view.my;

import com.jiaye.cashloan.view.AuthView;
import com.jiaye.cashloan.view.BasePresenter;

/**
 * MyContract
 *
 * @author 贾博瑄
 */

public class MyContract {

    interface View extends AuthView {

        void setBtnText(String text);

        void showAccountView();

        void showPlanView();
    }

    interface Presenter extends BasePresenter {

        void update();

        void loginOrLogout();

        void bank();

        void plan();
    }
}
