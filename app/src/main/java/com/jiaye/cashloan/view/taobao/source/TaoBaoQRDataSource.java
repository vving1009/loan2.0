package com.jiaye.cashloan.view.taobao.source;

import android.graphics.Bitmap;

import com.jiaye.cashloan.http.data.loan.SaveTaoBao;
import com.jiaye.cashloan.http.gongxinbao.GongXinBao;

import io.reactivex.Flowable;

/**
 * TaoBaoQRDataSource
 *
 * @author 贾博瑄
 */

public interface TaoBaoQRDataSource {

    Flowable<Bitmap> requestQRCode();

    Flowable<GongXinBao> requestTaoBaoLoginStatus();

    Bitmap getBitmap();

    String getRpc();

    Flowable<SaveTaoBao> requestSaveTaoBao(String token);
}
