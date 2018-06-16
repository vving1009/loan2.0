package com.jiaye.cashloan.view.my.source;

import com.jiaye.cashloan.http.data.my.CheckAccount;
import com.jiaye.cashloan.http.data.plan.Plan;
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

    Flowable<CheckAccount> checkBank();

    Flowable<Plan> checkPlan();
}
