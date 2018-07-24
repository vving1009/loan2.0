package com.jiaye.cashloan.view.register.source;

import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.login.Login;

import io.reactivex.Flowable;

/**
 * RegisterDataSource
 *
 * @author 贾博瑄
 */

public interface RegisterDataSource {

    Flowable<Login> requestRegister(String phone, String code, String password);

    Flowable<EmptyResponse> requestRegisterCode(String phone);
}
