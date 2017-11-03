package com.jiaye.loan.cashloan.view.view.auth.register;

import com.jiaye.loan.cashloan.http.data.auth.register.RegisterRequest;
import com.jiaye.loan.cashloan.http.data.auth.register.RegisterVerificationCodeRequest;
import com.jiaye.loan.cashloan.view.BasePresenter;
import com.jiaye.loan.cashloan.view.BaseView;

/**
 * RegisterContract
 *
 * @author 贾博瑄
 */

public class RegisterContract {

    interface View extends BaseView<Presenter> {

        /**
         * 获取输入的图形验证码
         */
        String getInputImgVerificationCode();

        /**
         * 获取图形验证码
         */
        String getImgVerificationCode();

        /**
         * 短信验证码倒计时
         */
        void smsVerificationCodeCountDown();

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

        void verificationCode(RegisterVerificationCodeRequest request);

        void register(RegisterRequest request);
    }
}
