package com.jiaye.cashloan.view.loancredit.certification;

import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * CertificationContract
 *
 * @author 贾博瑄
 */

public interface CertificationContract {

    interface View extends BaseViewContract {

        void setStep(int step);
    }

    interface Presenter extends BasePresenter {

        void requestStep();
    }
}
