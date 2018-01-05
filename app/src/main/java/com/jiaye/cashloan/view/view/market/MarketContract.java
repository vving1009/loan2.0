package com.jiaye.cashloan.view.view.market;

import com.jiaye.cashloan.http.data.my.User;
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
        /**
         * 得到用户信息
         */
        void getUserInfo(User user);
    }

    interface Presenter extends BasePresenter {
        /**
         * 检测是否登录
         */
       void checklogin();

        /**
         * 查询用户
         */
        void queryUser();
    }
}
