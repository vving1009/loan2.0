package com.jiaye.cashloan.view.view.my.certificate.info;

import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * InfoPersonContract
 *
 * @author 贾博瑄
 */

public interface InfoPersonContract {

    interface View extends BaseViewContract {

        void setEducation(String education);

        void setMarriage(String marriage);

        void setRegisterCity(String registerCity);

        void setCity(String city);

        void setAddress(String address);

        void setEmail(String email);
    }

    interface Presenter extends BasePresenter {
    }
}
