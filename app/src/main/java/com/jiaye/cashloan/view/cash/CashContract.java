package com.jiaye.cashloan.view.cash;

import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * CashContract
 *
 * @author 贾博瑄
 */

public interface CashContract {

    interface View extends BaseViewContract {

        void showCashView(String cash, String bank);
    }

    interface Presenter extends BasePresenter {

        void cash(String cash, String cashLimit, String bank);
    }
}
