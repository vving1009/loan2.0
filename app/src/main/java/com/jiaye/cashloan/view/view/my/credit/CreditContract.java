package com.jiaye.cashloan.view.view.my.credit;

import com.jiaye.cashloan.http.data.my.CreditPasswordRequest;
import com.jiaye.cashloan.http.data.my.CreditPasswordResetRequest;
import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * CreditContract
 *
 * @author 贾博瑄
 */

public interface CreditContract {

    interface View extends BaseViewContract {

        void notOpen();

        void setPasswordText(String text);

        void showPasswordView(CreditPasswordRequest request);

        void showPasswordResetView(CreditPasswordResetRequest request);

        void showCashView(String cash);

        void showBalance(String availBal, String freezeBal, String currBal);

        void dismissCash();
    }

    interface Presenter extends BasePresenter {

        void password();

        void cash(String cash);

        void balance();
    }
}
