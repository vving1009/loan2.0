package com.jiaye.cashloan.view.view.auth.password;

import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * ForgetPasswordContract
 *
 * @author 贾博瑄
 */

public class ForgetPasswordContract {

    interface View extends BaseViewContract {

        /**
         * 设置手机号
         */
        void setPhone(String text);

        /**
         * 设置按钮状态
         */
        void setEnable(boolean enable);

        /**
         * 获取手机号
         */
        String getPhone();

        /**
         * 获取输入的短信验证码
         */
        String getInputSmsVerificationCode();

        /**
         * 短信验证码倒计时
         */
        void smsVerificationCodeCountDown();

        /**
         * 显示修改密码页面
         */
        void showChangePasswordView();
    }

    interface Presenter extends BasePresenter {

        void checkInput();

        void setType(int type);

        void verificationCode();

        void next();
    }
}
