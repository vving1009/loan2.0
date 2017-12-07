package com.jiaye.cashloan.view.view.my.certificate.operator;

import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * OperatorContract
 *
 * @author 贾博瑄
 */

public interface OperatorContract {

    interface View extends BaseViewContract {

        void setPhone(String text);

        void setOperators(String text);
    }

    interface Presenter extends BasePresenter {
    }
}
