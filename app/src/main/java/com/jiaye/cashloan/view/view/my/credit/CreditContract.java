package com.jiaye.cashloan.view.view.my.credit;

import com.jiaye.cashloan.http.data.my.CreditBalance;
import com.jiaye.cashloan.http.data.my.CreditInfo;
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

        void setPasswordText(String text);

        void showBindBankView();

        void showPasswordView(CreditPasswordRequest request);

        void showPasswordResetView(CreditPasswordResetRequest request);

        void showCashView(CreditBalance balance);

        void showBalance(String balance);

        void showAccountId(String accountId);

        void showOpenDialog();

        void showBankView(boolean bind, CreditInfo creditInfo);
    }

    interface Presenter extends BasePresenter {

        void init();

        void account();

        void password();

        void cash();

        void bank();
    }
}
