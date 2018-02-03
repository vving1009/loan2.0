package com.jiaye.cashloan.view.view.my.credit.cash;

import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * CreditCashContract
 *
 * @author 贾博瑄
 */

public interface CreditCashContract {

    interface View extends BaseViewContract {

        void showCashView(String cash, String bank);
    }

    interface Presenter extends BasePresenter {

        void cash(String cash, String cashLimit, String bank);
    }
}
