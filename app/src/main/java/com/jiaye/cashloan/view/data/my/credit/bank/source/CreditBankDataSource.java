package com.jiaye.cashloan.view.data.my.credit.bank.source;

import com.jiaye.cashloan.http.data.my.CreditUnBindBank;

import io.reactivex.Flowable;

/**
 * CreditBankDataSource
 *
 * @author 贾博瑄
 */

public interface CreditBankDataSource {

    Flowable<CreditUnBindBank> unBind();
}
