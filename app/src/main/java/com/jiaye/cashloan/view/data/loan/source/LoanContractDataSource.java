package com.jiaye.cashloan.view.data.loan.source;

import com.jiaye.cashloan.http.base.Request;
import com.jiaye.cashloan.http.data.loan.Contract;
import com.jiaye.cashloan.http.data.loan.WatchContractRequest;

import io.reactivex.Flowable;

/**
 * LoanContractDataSource
 *
 * @author 贾博瑄
 */

public interface LoanContractDataSource {

    Flowable<Request<WatchContractRequest>> watchContract(String loanId);

    Flowable<Contract> contract();
}
