package com.jiaye.cashloan.view.view.my.certificate.bank;

import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * BankContract
 *
 * @author 贾博瑄
 */

public class BankContract {

    interface View extends BaseViewContract {

        void setName(String text);

        void setPhone(String text);

        void setBank(String text);

        void setNumber(String text);
    }

    interface Presenter extends BasePresenter {
    }
}
