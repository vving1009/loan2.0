package com.jiaye.cashloan.view.view.guide;

import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * GuideContract
 *
 * @author 贾博瑄
 */

public interface GuideContract {

    interface View extends BaseViewContract {

        /**
         * 显示主页面
         */
        void showMainView();
    }

    interface Presenter extends BasePresenter {

        /**
         * 设置不再显示引导页
         */
        void setNotNeedGuide();
    }
}
