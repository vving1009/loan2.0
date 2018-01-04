package com.jiaye.cashloan.view.view.market;

import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * Created by guozihua on 2018/1/4.
 */

public class MarketContract {
    interface View extends BaseViewContract {

        /**
         * 是否登录
         */
        void isLogin(boolean login);
    }

    interface Presenter extends BasePresenter {
        /**
         * 检测是否登录
         */
       void checklogin();
    }
}
