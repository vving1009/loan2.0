package com.jiaye.cashloan.view.bindbank;

import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * BindBankContract
 *
 * @author 贾博瑄
 */

public interface BindBankContract {

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
