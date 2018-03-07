package com.jiaye.cashloan.view.view.my.credit.bank;

import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * CreditBankContract
 *
 * @author 贾博瑄
 */

public interface CreditBankContract {

    interface View extends BaseViewContract {

        void complete();
    }

    interface Presenter extends BasePresenter {

        void unBind();
    }
}
