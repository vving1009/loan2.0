package com.jiaye.cashloan.view.data.loan.source;

import com.jiaye.cashloan.http.data.home.Product;
import com.jiaye.cashloan.http.data.loan.LoanAuth;
import com.jiaye.cashloan.http.data.loan.LoanAuthRequest;
import com.jiaye.cashloan.view.data.auth.User;

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
    Flowable<Product> queryProduct();

    /**
     * 请求产品信息
     */
    Flowable<Product> requestProduct();

    /**
     * 查询用户信息
     */
    Flowable<User> queryUser();

    /**
     * 查询借款认证状态
     */
    Flowable<LoanAuth> requestLoanAuth(LoanAuthRequest request);
}
