package com.jiaye.cashloan.view.login.source;

import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.auth.login.Login;

import io.reactivex.Flowable;

/**
 * LoginDataSource
 *
 * @author 贾博瑄
 */

public interface LoginDataSource {

    Flowable<Login> requestLogin(String phone, String password);

    Flowable<EmptyResponse> requestVerificationCode(String phone);
}
