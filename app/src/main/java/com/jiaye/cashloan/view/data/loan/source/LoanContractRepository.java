package com.jiaye.cashloan.view.data.loan.source;

import com.jiaye.cashloan.http.base.Request;
import com.jiaye.cashloan.http.base.RequestContent;
import com.jiaye.cashloan.http.base.RequestHeader;
import com.jiaye.cashloan.http.data.loan.Contract;
import com.jiaye.cashloan.http.data.loan.ContractRequest;
import com.jiaye.cashloan.http.data.loan.WatchContractRequest;
import com.jiaye.cashloan.http.utils.ResponseTransformer;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

/**
 * LoanContractRepository
 *
 * @author 贾博瑄
 */

public class LoanContractRepository implements LoanContractDataSource {

    @Override
    public Flowable<Request<WatchContractRequest>> watchContract(String loanId) {
        Request<WatchContractRequest> request = new Request<>();
        RequestContent<WatchContractRequest> content = new RequestContent<>();
        List<WatchContractRequest> list = new ArrayList<>();
        WatchContractRequest watchContract = new WatchContractRequest();
        watchContract.setLoanId(loanId);
        list.add(watchContract);
        content.setBody(list);
        RequestHeader header = RequestHeader.create();
        header.setPhone("");
        content.setHeader(header);
        request.setContent(content);
        return Flowable.just(request);
    }

    @Override
    public Flowable<Contract> contract() {
        return Flowable.just(new ContractRequest())
                .compose(new ResponseTransformer<ContractRequest, Contract>("contract"));
    }
}
