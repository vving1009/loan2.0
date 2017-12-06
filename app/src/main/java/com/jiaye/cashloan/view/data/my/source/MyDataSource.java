package com.jiaye.cashloan.view.data.my.source;

import com.jiaye.cashloan.http.data.my.User;

import io.reactivex.Flowable;
import io.reactivex.Observable;


/**
 * MyDataSource
 *
 * @author 贾博瑄
 */

public interface MyDataSource {

    Flowable<User> requestUser();

    Flowable<User> queryUser();
}
