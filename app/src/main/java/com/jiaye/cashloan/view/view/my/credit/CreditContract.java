package com.jiaye.cashloan.view.view.my.credit;

import com.jiaye.cashloan.view.BaseViewContract;
import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.data.my.credit.CreditBalanceRequest;
import com.jiaye.cashloan.view.data.my.credit.CreditCashRequest;
import com.jiaye.cashloan.view.data.my.credit.CreditPasswordRequest;

/**
 * CreditContract
 *
 * @author 贾博瑄
 */

public interface CreditContract {

    interface View extends BaseViewContract {

        void showPasswordView(CreditPasswordRequest request);

        void showCashView(CreditCashRequest request);

        void showBalanceView(CreditBalanceRequest request);
    }

    interface Presenter extends BasePresenter {

        void password();

        void cash();

        void balance();
    }
}
