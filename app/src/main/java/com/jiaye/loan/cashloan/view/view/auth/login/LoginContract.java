package com.jiaye.loan.cashloan.view.view.auth.login;

import com.jiaye.loan.cashloan.view.BasePresenter;
import com.jiaye.loan.cashloan.view.BaseView;

/**
 * LoginContract
 *
 * @author 贾博瑄
 */

public class LoginContract {

    interface View extends BaseView<Presenter> {

        /**
         * 获取手机号
         */
        String getPhone();

        /**
         * 获取密码
         */
        String getPassword();

        /**
         * 关闭当前页面
         */
        void finish();
    }

    interface Presenter extends BasePresenter {

        /**
         * 登录
         */
        void login();
    }
}
