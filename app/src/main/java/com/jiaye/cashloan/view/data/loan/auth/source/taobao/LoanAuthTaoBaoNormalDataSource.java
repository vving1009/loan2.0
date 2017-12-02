package com.jiaye.cashloan.view.data.loan.auth.source.taobao;

import com.jiaye.cashloan.http.data.loan.SaveTaoBao;
import com.jiaye.cashloan.http.gongxinbao.GongXinBao;

import io.reactivex.Flowable;

/**
 * LoanAuthTaoBaoNormalDataSource
 *
 * @author 贾博瑄
 */

public interface LoanAuthTaoBaoNormalDataSource {

    Flowable<Object> requestGongXinBaoInit();

    Flowable<GongXinBao> requestSMS();

    Flowable<GongXinBao> requestIMG();

    Flowable<GongXinBao> requestSubmit(String account, String password);

    Flowable<GongXinBao> requestSubmitSecond(String sms, String img);

    Flowable<GongXinBao> requestTaoBaoLoginStatus();

    Flowable<SaveTaoBao> requestSaveTaoBao(String token);
}
