package com.jiaye.cashloan.view.taobao;

import android.graphics.Bitmap;

import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * TaoBaoNormalContract
 *
 * @author 贾博瑄
 */

public interface TaoBaoNormalContract {

    interface View extends BaseViewContract {

        void startCountDown();

        void setImg(Bitmap bitmap);

        String getAccount();

        String getPassword();

        String getSMSCode();

        String getImgCode();

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

        void submit();
    }
}
