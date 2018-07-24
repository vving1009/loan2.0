package com.jiaye.cashloan.view.forgetpassword.source;

import com.jiaye.cashloan.http.base.EmptyResponse;

import io.reactivex.Flowable;

/**
 * ForgetPasswordDataSource
 *
 * @author 贾博瑄
 */

public interface ForgetPasswordDataSource {

    Flowable<EmptyResponse> requestForgetPassword(String phone, String code, String password);

    Flowable<EmptyResponse> requestForgetPasswordCode(String phone);
}
