package com.jiaye.cashloan.view.login.source;

import android.net.Uri;

import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.login.Login;

import io.reactivex.Flowable;

/**
 * LoginDataSource
 *
 * @author 贾博瑄
 */

public interface LoginDataSource {

    Flowable<Login> requestLogin(String phone, String password);

    Flowable<EmptyResponse> requestVerificationCode(String phone);

    Flowable<String> querySmsCode(Uri uri);
}
