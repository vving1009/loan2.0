package com.jiaye.cashloan.view.view.auth.login;

import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * LoginContract
 *
 * @author 贾博瑄
 */

public class LoginContract {

    interface View extends BaseViewContract {

        /**
         * 设置按钮状态
         */
        void setLoginBtnEnable(boolean enable);

        /**
         * 设置按钮状态
         */
        void setSmsBtnEnable(boolean enable);

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
         * 输入检查
         */
        void checkInput();

        /**
         * 登录
         */
        void login();
    }
}
