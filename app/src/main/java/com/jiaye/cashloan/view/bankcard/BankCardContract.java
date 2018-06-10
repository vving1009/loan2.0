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

        void complete();

        void unBindFailedWrongCode();

        void unBindFailedBalance();
    }

    interface Presenter extends BasePresenter {

        void unBind();
    }
}
