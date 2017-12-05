package com.jiaye.cashloan.view.data.loan.source;

import com.jiaye.cashloan.http.data.loan.LoanProgress;
import com.jiaye.cashloan.http.data.loan.LoanProgressRequest;
import com.jiaye.cashloan.http.utils.ResponseTransformer;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * LoanProgressRepository
 *
 * @author 贾博瑄
 */

public class LoanProgressRepository implements LoanProgressDataSource {

    @Override
    public Flowable<LoanProgress> requestLoanProgress(String loanId) {
        return Flowable.just(loanId)
                .map(new Function<String, LoanProgressRequest>() {
                    @Override
                    public LoanProgressRequest apply(String loanId) throws Exception {
                        LoanProgressRequest request = new LoanProgressRequest();
                        request.setLoanId(loanId);
                        return request;
                    }
                })
                .compose(new ResponseTransformer<LoanProgressRequest, LoanProgress>("loanProgress"));
    }
}
