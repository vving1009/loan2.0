package com.jiaye.cashloan.view.view.loan;

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
         * 显示借款认证页面
         */
        void showLoanAuthView();
    }

    public interface Presenter extends BasePresenter {

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
