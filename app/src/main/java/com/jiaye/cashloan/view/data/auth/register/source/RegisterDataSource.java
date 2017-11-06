package com.jiaye.cashloan.view.data.auth.register.source;

import com.jiaye.cashloan.http.data.auth.register.Register;

/**
 * RegisterDataSource
 *
 * @author 贾博瑄
 */

public interface RegisterDataSource {

    /**
     * 增加用户信息
     */
    void addUser(Register register);
}
