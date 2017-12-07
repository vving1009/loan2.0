package com.jiaye.cashloan.view.data.my.certificate.bank.source;

import com.jiaye.cashloan.http.data.my.Bank;

import io.reactivex.Flowable;

/**
 * BankDataSource
 *
 * @author 贾博瑄
 */

public interface BankDataSource {

    Flowable<Bank> bank();
}
