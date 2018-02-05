package com.jiaye.cashloan.view.data.loan.source;

import com.jiaye.cashloan.http.data.loan.ContractList;

import io.reactivex.Flowable;

/**
 * LoanContractListDataSource
 *
 * @author 贾博瑄
 */

public interface LoanContractListDataSource {

    Flowable<ContractList.Contract[]> requestContractList(String loanId);
}
