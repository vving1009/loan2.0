package com.jiaye.cashloan.view.view.loan;

import com.jiaye.cashloan.http.data.home.Product;
import com.jiaye.cashloan.view.AuthView;
import com.jiaye.cashloan.view.BasePresenter;

/**
 * LoanContract
 *
 * @author 贾博瑄
 */

public class LoanContract {

    interface View extends AuthView {

        /**
         * 查询产品信息
         */
        void queryProduct();

        /**
         * 请求产品信息
         */
        void requestProduct();

        /**
         * 清空产品信息
         */
        void cleanProduct();

        /**
         * 设置产品信息
         */
        void setProduct(Product product);

        /**
         * 显示借款认证页面
         */
        void startLoanAuthView();
    }

    interface Presenter extends BasePresenter {

        /**
         * 查询产品信息
         */
        void queryProduct();

        /**
         * 请求产品信息
         */
        void requestProduct();

        /**
         * 借款
         */
        void loan();
    }
}
