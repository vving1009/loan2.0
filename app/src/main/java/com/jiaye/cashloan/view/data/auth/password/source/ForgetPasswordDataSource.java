package com.jiaye.cashloan.view.data.auth.password.source;

import io.reactivex.Flowable;

/**
 * ForgetPasswordDataSource
 *
 * @author 贾博瑄
 */

public interface ForgetPasswordDataSource {

    Flowable<String> queryPhone();
}
