package com.jiaye.cashloan.view.data.loan.auth.source.phone;

import android.graphics.Bitmap;

import com.jiaye.cashloan.http.data.loan.SavePhone;
import com.jiaye.cashloan.http.gongxinbao.GongXinBao;
import com.jiaye.cashloan.http.gongxinbao.GongXinBaoOperatorsConfig;

import io.reactivex.Flowable;

/**
 * LoanAuthPhoneDataSource
 *
 * @author 贾博瑄
 */

public interface LoanAuthPhoneDataSource {

    Flowable<GongXinBaoOperatorsConfig> requestGongXinBaoOperatorsConfig();

    Flowable<Bitmap> requestImgVerificationCode();

    Flowable<GongXinBao> requestSmsVerificationCode();

    Flowable<GongXinBao> requestSubmit(String password, String imgCode, String smsCode);

    Flowable<GongXinBao> requestOperatorLoginStatus();

    Flowable<GongXinBao> requestSubmitSecond(String imgCode, String smsCode);

    Flowable<SavePhone> requestSavePhone(String token);

    String getPhone();
}
