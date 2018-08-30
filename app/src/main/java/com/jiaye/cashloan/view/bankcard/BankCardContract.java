package com.jiaye.cashloan.view.bankcard;

import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * BankCardContract
 *
 * @author 贾博瑄
 */

public interface BankCardContract {

    interface View extends BaseViewContract {

        void showBindCardView();

        void showUnbindCardView(String account, String name, String bankNo);
    }

    interface Presenter extends BasePresenter {

        void init();
    }
}
