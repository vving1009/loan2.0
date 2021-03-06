package com.jiaye.cashloan.view.login;

import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * LoginNormalContract
 *
 * @author 贾博瑄
 */
public interface LoginNormalContract {

    interface View extends BaseViewContract {

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
