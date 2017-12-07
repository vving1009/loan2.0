package com.jiaye.cashloan.view.view.my.certificate.idcard;

import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * IdCardContract
 *
 * @author 贾博瑄
 */

public interface IdCardContract {

    interface View extends BaseViewContract {

        void setName(String text);

        void setNumber(String text);

        void setDate(String text);
    }

    interface Presenter extends BasePresenter {
    }
}
