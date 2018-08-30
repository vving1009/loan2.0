package com.jiaye.cashloan.view.bankcard.source;

import com.jiaye.cashloan.http.data.my.CreditInfo;

import io.reactivex.Flowable;

/**
 * BankCardDataSource
 *
 * @author 贾博瑄
 */

public interface BankCardDataSource {

    Flowable<CreditInfo> creditInfo();
}
