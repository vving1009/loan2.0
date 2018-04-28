package com.jiaye.cashloan.view.data.auth.register.source;

import com.jiaye.cashloan.http.data.auth.VerificationCode;
import com.jiaye.cashloan.http.data.auth.register.Register;

import io.reactivex.Flowable;

/**
 * RegisterDataSource
 *
 * @author 贾博瑄
 */

public interface RegisterDataSource {

    Flowable<VerificationCode> requestVerificationCode(String phone);

    /**
     * 增加用户信息
     */
    void addUser(Register register);
}
