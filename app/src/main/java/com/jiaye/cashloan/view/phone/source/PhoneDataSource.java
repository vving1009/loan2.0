package com.jiaye.cashloan.view.phone.source;

import android.graphics.Bitmap;

import com.jiaye.cashloan.http.data.loan.SavePhone;
import com.jiaye.cashloan.http.gongxinbao.GongXinBao;
import com.jiaye.cashloan.http.gongxinbao.GongXinBaoOperatorsConfig;

import io.reactivex.Flowable;

/**
 * PhoneDataSource
 *
 * @author 贾博瑄
 */

public interface PhoneDataSource {

    Flowable<GongXinBaoOperatorsConfig> requestGongXinBaoOperatorsConfig();

    Flowable<Bitmap> requestImgVerificationCode();

    Flowable<GongXinBao> requestSmsVerificationCode();

    Flowable<GongXinBao> requestSubmit(String password, String imgCode, String smsCode);

    Flowable<GongXinBao> requestOperatorLoginStatus();

    Flowable<GongXinBao> requestSubmitSecond(String imgCode, String smsCode);

    Flowable<SavePhone> requestSavePhone(String token);

    String getPhone();
}
