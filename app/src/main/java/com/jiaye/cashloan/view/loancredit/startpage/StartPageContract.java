package com.jiaye.cashloan.view.loancredit.startpage;

import com.jiaye.cashloan.view.BaseViewContract;
import com.jiaye.cashloan.view.BasePresenter;

/**
 * StartPageContract
 *
 * @author 贾博瑄
 */

public interface StartPageContract {

    interface View extends BaseViewContract {

        void showCertificationView();
    }

    interface Presenter extends BasePresenter {
        void loan();
    }
}
