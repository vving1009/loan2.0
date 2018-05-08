package com.jiaye.cashloan.view.view.loan.auth.phone;

import android.graphics.Bitmap;

import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * LoanAuthPhoneContract
 *
 * @author 贾博瑄
 */

public interface LoanAuthPhoneContract {

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
