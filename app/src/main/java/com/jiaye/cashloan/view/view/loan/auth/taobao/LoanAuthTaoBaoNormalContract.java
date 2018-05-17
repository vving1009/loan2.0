package com.jiaye.cashloan.view.view.loan.auth.taobao;

import android.graphics.Bitmap;

import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * LoanAuthTaoBaoNormalContract
 *
 * @author 贾博瑄
 */

public interface LoanAuthTaoBaoNormalContract {

    interface View extends BaseViewContract {

        void startCountDown();

        void setImg(Bitmap bitmap);

        String getAccount();

        String getPassword();

        String getSMSCode();

        String getImgCode();

        void setEnable(boolean enable);

        void cleanImgVerificationCodeText();

        void setImgVerificationCodeVisibility();

        void setImgVerificationCode(Bitmap bitmap);

        void cleanSmsVerificationCodeText();

        void setSmsVerificationCodeVisibility();

        void result();
    }

    interface Presenter extends BasePresenter {

        void requestSMS();

        void requestIMG();

        void checkInput();

        void submit();
    }
}
