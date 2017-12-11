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

        void setImgVerificationCodeVisibility(int visibility);

        void setSmsVerificationCodeVisibility(int visibility);

        void setImgVerificationCode(Bitmap bitmap);

        void setSmsVerificationCodeCountDown();

        void cleanImgVerificationCodeText();

        void cleanSmsVerificationCodeText();

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
