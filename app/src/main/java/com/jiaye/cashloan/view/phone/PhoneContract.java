package com.jiaye.cashloan.view.phone;

import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * PhoneContract
 *
 * @author 贾博瑄
 */

public interface PhoneContract {

    interface View extends BaseViewContract {

        void exit();
    }

    interface Presenter extends BasePresenter {

        void requestUpdatePhone();
    }
}
