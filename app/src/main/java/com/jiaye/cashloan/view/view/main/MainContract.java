package com.jiaye.cashloan.view.view.main;

import com.jiaye.cashloan.view.AuthView;
import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * Created by guozihua on 2018/1/3.
 */

public class MainContract {

    interface View extends AuthView {
        /**
         * 已经登录
         */
        void isLogin();
    }

    interface Presenter extends BasePresenter {
        /**
         * 检查是否登录
         */
       void  requestCheck() ;
    }
}
