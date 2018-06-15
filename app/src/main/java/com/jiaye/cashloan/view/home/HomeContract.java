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
         * 显示搜索公司页面
         */
        void showCompanyView();

        /**
         * 显示认证页面
         */
        void showCertificationView();
    }

    interface Presenter extends BasePresenter {

        /**
         * 申请借款
         */
        void loan();
    }
}
