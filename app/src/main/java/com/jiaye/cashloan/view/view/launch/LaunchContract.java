package com.jiaye.cashloan.view.view.launch;

import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * LaunchContract
 *
 * @author 贾博瑄
 */

public interface LaunchContract {

    interface View extends BaseViewContract {

        /**
         * 显示引导页
         */
        void showGuideView();

        /**
         * 显示主页面
         */
        void showMainView();
    }

    interface Presenter extends BasePresenter {

    }
}
