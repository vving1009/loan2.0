package com.jiaye.cashloan.view.forgetpassword;

import com.jiaye.cashloan.view.BaseViewContract;
import com.jiaye.cashloan.view.BasePresenter;

/**
 * ForgetPasswordContract
 *
 * @author 贾博瑄
 */

public interface ForgetPasswordContract {

    interface View extends BaseViewContract {

        /**
         * 获取手机号
         */
        String getPhone();

        /**
         * 获取验证码
         */
        String getCode();

        /**
         * 获取密码
         */
        String getPassword();

        /**
         * 关闭当前页面
         */
        void finish();

        /**
         * 验证码倒计时
         */
        void smsVerificationCodeCountDown();
    }

    interface Presenter extends BasePresenter {

        /**
         * 设置密码
         */
        void password();

        /**
         * 发送验证码
         */
        void verificationCode();
    }
}
