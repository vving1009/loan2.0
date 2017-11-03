package com.jiaye.loan.cashloan.view.data.auth.login.source;

import com.jiaye.loan.cashloan.http.data.auth.login.Login;
import com.jiaye.loan.cashloan.view.data.BaseDataSource;

/**
 * LoginDataSource
 *
 * @author 贾博瑄
 */

public interface LoginDataSource extends BaseDataSource {

    /**
     * 增加用户信息
     */
    void addUser(Login login);
}
