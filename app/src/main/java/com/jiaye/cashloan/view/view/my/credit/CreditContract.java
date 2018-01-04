package com.jiaye.cashloan.view.view.my.credit;

import com.jiaye.cashloan.http.data.my.CreditPasswordResetRequest;
import com.jiaye.cashloan.view.BaseViewContract;
import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.http.data.my.CreditBalanceRequest;
import com.jiaye.cashloan.http.data.my.CreditCashRequest;
import com.jiaye.cashloan.http.data.my.CreditPasswordRequest;

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

        void showCashView(CreditCashRequest request);

        void showBalance(String availBal,String currBal);
    }

    interface Presenter extends BasePresenter {

        void password();

        void cash();

        void balance();
    }
}
