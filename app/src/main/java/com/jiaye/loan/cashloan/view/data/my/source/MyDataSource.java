package com.jiaye.loan.cashloan.view.data.my.source;

import com.jiaye.loan.cashloan.view.data.BaseDataSource;
import com.jiaye.loan.cashloan.view.data.auth.User;

import io.reactivex.Observable;


/**
 * MyDataSource
 *
 * @author 贾博瑄
 */

public interface MyDataSource extends BaseDataSource {

    /**
     * 请求用户信息
     * @return 用户信息
     */
    Observable<User> requestUser();

    /**
     * 查询用户信息
     *
     * @return 用户信息
     */
    Observable<User> queryUser();
}
