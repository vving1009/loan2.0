package com.jiaye.cashloan.view.home.source;

import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.home.CheckCompany;
import com.jiaye.cashloan.http.data.loan.Loan;
import com.jiaye.cashloan.persistence.User;

import io.reactivex.Flowable;

/**
 * HomeDataSource
 *
 * @author 贾博瑄
 */

public interface HomeDataSource {

    /**
     * 查询本地是否有用户数据
     */
    Flowable<User> queryUser();

    /**
     * 申请借款
     */
    Flowable<Loan> requestLoan();

    /**
     * 上传危险应用列表
     */
    Flowable<EmptyResponse> uploadRiskAppList();

    /**
     * 用户是否需要选择分公司
     */
    Flowable<CheckCompany> checkCompany();
}
