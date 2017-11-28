package com.jiaye.cashloan.view.view.loan.auth.face;

import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * LoanAuthFaceContract
 *
 * @author 贾博瑄
 */

public interface LoanAuthFaceContract {

    interface View extends BaseViewContract {

        void result();
    }

    interface Presenter extends BasePresenter {

        void commit(byte[] verification);
    }
}
