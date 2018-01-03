package com.jiaye.cashloan.view.view.loan;

import com.jiaye.cashloan.http.data.loan.DefaultProduct;
import com.jiaye.cashloan.view.AuthView;
import com.jiaye.cashloan.view.BasePresenter;

/**
 * LoanContract
 *
 * @author 贾博瑄
 */

public class LoanContract {

   public interface View extends AuthView {

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
         * 设置默认产品信息
         */
        void setDefaultProduct(DefaultProduct defaultProduct);

        /**
         * 显示借款认证页面
         */
        void showLoanAuthView();
   }

    public interface Presenter extends BasePresenter {

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
