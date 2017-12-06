package com.jiaye.cashloan.view.view.my.settings;

import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * SettingsContract
 *
 * @author 贾博瑄
 */

public interface SettingsContract {

    interface View extends BaseViewContract {

        void result();
    }

    interface Presenter extends BasePresenter {

        void exit();
    }
}
