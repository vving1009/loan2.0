package com.jiaye.cashloan.view.login;

import android.net.Uri;

import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * LoginShortcutContract
 *
 * @author 贾博瑄
 */

public class LoginShortcutContract {

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
         * 显示提示对话框
         */
        void showHintDialog();

        /**
         * 显示密码输入框
         */
        void showPasswordView();

        /**
         * 隐藏密码输入框
         */
        void hidePasswordView();

        /**
         * 获取是否显示密码输入框
         */
        boolean isShowPasswordView();

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

        /**
         * 填写验证码
         */
        void writeSmsCode(String code);
    }

    interface Presenter extends BasePresenter {

        /**
         * 登录
         */
        void login();

        /**
         * 发送验证码
         */
        void verificationCode();

        /**
         * 自动获取验证码
         */
        void getSmsCode(Uri uri);
    }
}
