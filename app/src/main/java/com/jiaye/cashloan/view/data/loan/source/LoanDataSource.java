package com.jiaye.cashloan.view.data.loan.source;

import com.jiaye.cashloan.http.data.loan.DefaultProduct;
import com.jiaye.cashloan.http.data.loan.LoanAuth;
import com.jiaye.cashloan.http.data.my.User;

import io.reactivex.Flowable;

/**
 * LoanDataSource
 *
 * @author 贾博瑄
 */

public interface LoanDataSource {

    /**
     * 查询产品信息
     */
    Flowable<DefaultProduct> queryProduct();

    /**
     * 查询默认产品信息
     */
    Flowable<DefaultProduct> queryDefaultProduct();

    /**
     * 请求默认产品信息
     */
    Flowable<DefaultProduct> requestProduct();

    /**
     * 请求检查是否可以借款
     */
    Flowable<LoanAuth> requestCheck();
}
