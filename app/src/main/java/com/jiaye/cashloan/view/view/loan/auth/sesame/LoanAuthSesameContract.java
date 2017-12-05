package com.jiaye.cashloan.view.view.loan.auth.sesame;

import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * LoanAuthSesameContract
 *
 * @author 贾博瑄
 */

public interface LoanAuthSesameContract {

    interface View extends BaseViewContract {

        void postUrl(String url, byte[] postData);
    }

    interface Presenter extends BasePresenter {
    }
}
