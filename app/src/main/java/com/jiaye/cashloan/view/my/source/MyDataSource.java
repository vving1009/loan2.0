package com.jiaye.cashloan.view.my.source;

import com.jiaye.cashloan.persistence.User;

import io.reactivex.Flowable;


/**
 * MyDataSource
 *
 * @author 贾博瑄
 */

public interface MyDataSource {

    Flowable<User> queryUser();

    Flowable<Boolean> exit();

    Flowable<Boolean> checkBank();

    Flowable<Boolean> checkPlan();
}
