package com.jiaye.cashloan.view.data.loan.auth.source.taobao;

import android.graphics.Bitmap;

import com.jiaye.cashloan.http.data.loan.SaveTaoBao;
import com.jiaye.cashloan.http.gongxinbao.GongXinBao;

import io.reactivex.Flowable;

/**
 * LoanAuthTaoBaoQRDataSource
 *
 * @author 贾博瑄
 */

public interface LoanAuthTaoBaoQRDataSource {

    Flowable<Bitmap> requestQRCode();

    Flowable<GongXinBao> requestTaoBaoLoginStatus();

    Bitmap getBitmap();

    String getRpc();

    Flowable<SaveTaoBao> requestSaveTaoBao(String token);
}
