package com.jiaye.cashloan.view.account;

import com.jiaye.cashloan.http.data.my.CreditBalance;
import com.jiaye.cashloan.http.data.my.CreditPasswordRequest;
import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * AccountContract
 *
 * @author 贾博瑄
 */

public interface AccountContract {

    interface View extends BaseViewContract {

        void setPasswordText(String text);

        void showBindBankView();

        void showPasswordView(CreditPasswordRequest request);

        void showAuthView();

        void showPasswordResetView();

        void showCashView(CreditBalance balance);

        void showBalance(String balance);

        void showAccountId(String accountId);

        void showOpenDialog();

        void showBankView();
    }

    interface Presenter extends BasePresenter {

        void init();

        void account();

        void password();

        void auth();

        void cash();

        void bank();
    }
}
