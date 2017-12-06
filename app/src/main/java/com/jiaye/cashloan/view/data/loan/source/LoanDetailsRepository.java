package com.jiaye.cashloan.view.data.loan.source;

import com.jiaye.cashloan.http.data.loan.LoanDetails;
import com.jiaye.cashloan.http.data.loan.LoanDetailsRequest;
import com.jiaye.cashloan.http.utils.ResponseTransformer;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * LoanDetailsRepository
 *
 * @author 贾博瑄
 */

public class LoanDetailsRepository implements LoanDetailsDataSource {

    @Override
    public Flowable<LoanDetails> requestLoanDetails(String id) {
        return Flowable.just(id)
                .map(new Function<String, LoanDetailsRequest>() {
                    @Override
                    public LoanDetailsRequest apply(String id) throws Exception {
                        LoanDetailsRequest request = new LoanDetailsRequest();
                        request.setLoanId(id);
                        return request;
                    }
                })
                .compose(new ResponseTransformer<LoanDetailsRequest, LoanDetails>("loanDetails"));
    }
}
