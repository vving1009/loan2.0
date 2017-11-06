package com.jiaye.cashloan.view.data.auth.login.source;

import com.jiaye.cashloan.http.data.auth.login.Login;

/**
 * LoginDataSource
 *
 * @author 贾博瑄
 */

public interface LoginDataSource {

    /**
     * 增加用户信息
     */
    void addUser(Login login);
}
