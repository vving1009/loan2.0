package com.jiaye.cashloan.view.bindbank.source;

import com.jiaye.cashloan.http.data.loan.LoanBindBank;
import com.jiaye.cashloan.http.data.loan.LoanBindBankRequest;
import com.jiaye.cashloan.http.data.loan.LoanOpenSMS;
import com.jiaye.cashloan.http.data.loan.LoanOpenSMSRequest;

import io.reactivex.Flowable;

/**
 * BindBankDataSource
 *
 * @author 贾博瑄
 */

public interface BindBankDataSource {

    Flowable<String> queryName();

    Flowable<LoanOpenSMS> requestBindBankSMS(LoanOpenSMSRequest request);

    Flowable<LoanBindBank> requestBindBank(LoanBindBankRequest request);

    Flowable<LoanBindBank> requestBindBankAgain(LoanBindBankRequest request);
}
