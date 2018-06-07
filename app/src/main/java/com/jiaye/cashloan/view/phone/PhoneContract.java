package com.jiaye.cashloan.view.phone;

import android.graphics.Bitmap;

import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * PhoneContract
 *
 * @author 贾博瑄
 */

public interface PhoneContract {

    interface View extends BaseViewContract {

        void setPhone(String text);

        void setOperators(String text);

        void setPasswordVisibility(int visibility);

        void setForgetPasswordVisibility(int visibility);

        void addSms();

        void addImg();

        void firstSubmit();

        void setImgVerificationCode(Bitmap bitmap);

        void setSmsVerificationCodeCountDown();

        String getPassword();

        String getSmsVerificationCode();

        String getImgVerificationCode();

        boolean isSmsVerificationCodeVisibility();

        boolean isImgVerificationCodeVisibility();

        void result();
    }

    interface Presenter extends BasePresenter {

        void requestSMSVerification();

        void requestIMGVerification();

        void submit();
    }
}
