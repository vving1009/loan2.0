package com.jiaye.cashloan.view.data.loan.source;

import com.jiaye.cashloan.http.base.Request;
import com.jiaye.cashloan.http.base.RequestContent;
import com.jiaye.cashloan.http.base.RequestHeader;
import com.jiaye.cashloan.http.data.loan.WatchContractRequest;

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
    public Flowable<Request<WatchContractRequest>> watchContract(String contractId) {
        Request<WatchContractRequest> request = new Request<>();
        RequestContent<WatchContractRequest> content = new RequestContent<>();
        List<WatchContractRequest> list = new ArrayList<>();
        WatchContractRequest watchContract = new WatchContractRequest();
        watchContract.setContractId(contractId);
        list.add(watchContract);
        content.setBody(list);
        content.setHeader(RequestHeader.create());
        request.setContent(content);
        return Flowable.just(request);
    }
}
