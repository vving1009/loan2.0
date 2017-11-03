package com.jiaye.loan.cashloan.view.view.auth.password;

import com.jiaye.loan.cashloan.view.BasePresenter;
import com.jiaye.loan.cashloan.view.BaseView;

/**
 * ChangePasswordContract
 *
 * @author 贾博瑄
 */

public class ChangePasswordContract {

    interface View extends BaseView<Presenter> {

        /**
         * 获取手机号
         */
        String getPhone();

        /**
         * 获取第一次输入的密码
         */
        String getPassword();

        /**
         * 获取第二次输入的密码
         */
        String getPasswordSecond();

        /**
         * 结束
         */
        void finish();
    }

    interface Presenter extends BasePresenter {

        void save();
    }
}
