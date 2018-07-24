package com.jiaye.cashloan.view.login.source;

import com.jiaye.cashloan.http.data.login.Login;

import io.reactivex.Flowable;

/**
 * LoginNormalDataSource
 *
 * @author 贾博瑄
 */
public interface LoginNormalDataSource {

    Flowable<Login> requestLogin(String phone, String password);
}
