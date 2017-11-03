package com.jiaye.loan.cashloan.view.view.my;

import com.jiaye.loan.cashloan.view.AuthView;
import com.jiaye.loan.cashloan.view.BasePresenter;
import com.jiaye.loan.cashloan.view.BaseView;
import com.jiaye.loan.cashloan.view.data.auth.User;

/**
 * MyContract
 *
 * @author 贾博瑄
 */

public class MyContract {

    interface View extends AuthView<Presenter> {

        /**
         * 显示用户信息
         *
         * @param user 用户信息
         */
        void showUserInfo(User user);

        /**
         * 开启我的认证页面
         *
         * @param user 用户信息
         */
        void startMyCertificateView(User user);
    }

    interface Presenter extends BasePresenter {

        /**
         * 点击我的认证
         */
        void onClickMyCertificate();
    }
}
