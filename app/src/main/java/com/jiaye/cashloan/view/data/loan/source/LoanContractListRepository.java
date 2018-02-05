package com.jiaye.cashloan.view.data.loan.source;

import com.jiaye.cashloan.http.data.loan.ContractList;
import com.jiaye.cashloan.http.data.loan.ContractListRequest;
import com.jiaye.cashloan.http.utils.ResponseTransformer;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * LoanContractListRepository
 *
 * @author 贾博瑄
 */

public class LoanContractListRepository implements LoanContractListDataSource {

    @Override
    public Flowable<ContractList.Contract[]> requestContractList(String loanId) {
        return Flowable.just(loanId)
                .map(new Function<String, ContractListRequest>() {
                    @Override
                    public ContractListRequest apply(String loanId) throws Exception {
                        ContractListRequest request = new ContractListRequest();
                        request.setLoanId(loanId);
                        return request;
                    }
                })
                .compose(new ResponseTransformer<ContractListRequest, ContractList>("contractList"))
                .map(new Function<ContractList, ContractList.Contract[]>() {
                    @Override
                    public ContractList.Contract[] apply(ContractList contractList) throws Exception {
                        return contractList.getContracts();
                    }
                });
    }
}
