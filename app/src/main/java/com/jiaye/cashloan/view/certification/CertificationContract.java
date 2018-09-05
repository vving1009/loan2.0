package com.jiaye.cashloan.view.certification;

import com.jiaye.cashloan.view.BaseViewContract;
import com.jiaye.cashloan.view.BasePresenter;

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
