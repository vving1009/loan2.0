package com.jiaye.cashloan.view.data.main;

import com.jiaye.cashloan.http.data.my.User;

import io.reactivex.Flowable;

/**
 * Created by guozihua on 2018/1/3.
 */

public interface MainDataSource {
    /**
     * 检查登录
     */
    Flowable<User> queryUser();
}
