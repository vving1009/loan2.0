package com.jiaye.cashloan.view.data.loan.source;

import com.jiaye.cashloan.http.data.loan.LoanBindBank;
import com.jiaye.cashloan.http.data.loan.LoanBindBankRequest;

import io.reactivex.Flowable;

/**
 * LoanBindBankDataSource
 *
 * @author 贾博瑄
 */

public interface LoanBindBankDataSource {

    Flowable<String> queryName();

    Flowable<LoanBindBank> requestBindBank(LoanBindBankRequest request);
}
