package com.jiaye.loan.cashloan.view.data.auth.register.source;

import com.jiaye.loan.cashloan.http.data.auth.register.Register;
import com.jiaye.loan.cashloan.view.data.BaseDataSource;

/**
 * RegisterDataSource
 *
 * @author 贾博瑄
 */

public interface RegisterDataSource extends BaseDataSource {

    /**
     * 增加用户信息
     */
    void addUser(Register register);
}
