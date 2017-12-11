package com.jiaye.cashloan.view.view.auth.password;

import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * ChangePasswordContract
 *
 * @author 贾博瑄
 */

public class ChangePasswordContract {

    interface View extends BaseViewContract {

        /**
         * 设置按钮状态
         */
        void setEnable(boolean enable);

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

        void checkInput();

        void setType(int type);

        void save();
    }
}
