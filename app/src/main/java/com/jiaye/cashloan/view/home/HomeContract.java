package com.jiaye.cashloan.view.home;

import com.jiaye.cashloan.view.AuthView;
import com.jiaye.cashloan.view.BasePresenter;

/**
 * HomeContract
 *
 * @author 贾博瑄
 */
public interface HomeContract {

    interface View extends AuthView {

        /**
         * 显示借款认证页面
         */
        void showLoanAuthView();
    }

    interface Presenter extends BasePresenter {

        /**
         * 借款
         */
        void loan(String loanId);
    }
}
