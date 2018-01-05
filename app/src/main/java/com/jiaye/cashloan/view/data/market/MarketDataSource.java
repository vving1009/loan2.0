package com.jiaye.cashloan.view.data.market;

import com.jiaye.cashloan.http.data.my.User;

import io.reactivex.Flowable;

/**
 * Created by guozihua on 2018/1/4.
 */

public interface MarketDataSource {
    /**
     * 检测登录
     * @return
     */
    Flowable<Boolean> checkLogin() ;
    /**
     * 查询用户
     */

     Flowable<User> queryUser() ;
}
