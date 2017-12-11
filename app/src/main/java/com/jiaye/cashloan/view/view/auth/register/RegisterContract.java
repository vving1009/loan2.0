package com.jiaye.cashloan.view.view.auth.register;

import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * RegisterContract
 *
 * @author 贾博瑄
 */

public class RegisterContract {

    interface View extends BaseViewContract {

        /**
         * 设置按钮状态
         */
        void setEnable(boolean enable);

        /**
         * 获取手机号
         */
        String getPhone();

        /**
         * 获取输入的图形验证码
         */
        String getInputImgVerificationCode();

        /**
         * 获取图形验证码
         */
        String getImgVerificationCode();

        /**
         * 获取密码
         */
        String getPassword();

        /**
         * 获取输入的短信验证码
         */
        String getInputSmsVerificationCode();

        /**
         * 短信验证码倒计时
         */
        void smsVerificationCodeCountDown();

        /**
         * 获取输入的短信验证码
         */
        String getReferralCode();

        /**
         * 用户是否同意注册协议
         */
        boolean isAgree();

        /**
         * 关闭当前页面
         */
        void finish();
    }

    interface Presenter extends BasePresenter {

        /**
         * 输入校验
         */
        void checkInput();

        /**
         * 请求验证码
         */
        void verificationCode();

        /**
         * 注册
         */
        void register();
    }
}
