package com.jiaye.cashloan.view.data.auth.register.source;

import com.jiaye.cashloan.http.data.auth.VerificationCode;
import com.jiaye.cashloan.http.data.auth.login.Login;

import io.reactivex.Flowable;

/**
 * RegisterDataSource
 *
 * @author 贾博瑄
 */

public interface RegisterDataSource {

    /**
     * 注册获取验证码
     */
    Flowable<VerificationCode> requestVerificationCode(String phone);

    /**
     * 注册
     */
    Flowable<Login> requestRegister(String phone, String password, String sms, String code);
}
