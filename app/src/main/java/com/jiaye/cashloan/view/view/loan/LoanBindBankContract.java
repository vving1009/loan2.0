package com.jiaye.cashloan.view.view.loan;

import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * LoanBindBankContract
 *
 * @author 贾博瑄
 */

public interface LoanBindBankContract {

    interface View extends BaseViewContract {

        void setName(String text);

        String getPhone();

        String getBank();

        String getNumber();

        String getSMS();

        void startCountDown();

        /**
         * 开户成功
         */
        void complete();

        void result();
    }

    interface Presenter extends BasePresenter {

        void setSource(String source);

        void requestSMS();

        void submit();
    }
}
