package com.jiaye.cashloan.view.view.loan.auth.visa;

import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * LoanAuthVisaContract
 *
 * @author 贾博瑄
 */

public interface LoanAuthVisaContract {

    interface View extends BaseViewContract {

        void postUrl(String url, byte[] postData);

        void showSMSDialog();

        void dismissSMSDialog();

        void hideBtn();
    }

    interface Presenter extends BasePresenter {

        void setType(String type);

        void sendSMS();

        void sign(String sms);
    }
}
