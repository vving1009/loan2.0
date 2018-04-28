package com.jiaye.cashloan.view.data.loan.source;

import com.jiaye.cashloan.http.data.loan.LoanApply;
import com.jiaye.cashloan.http.data.loan.LoanApplyRequest;
import com.jiaye.cashloan.http.utils.SatcatcheResponseTransformer;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * LoanDetailsRepository
 *
 * @author 贾博瑄
 */

public class LoanDetailsRepository implements LoanDetailsDataSource {

    @Override
    public Flowable<LoanApply> requestLoanApply(String state) {
        return Flowable.just(state)
                .map(new Function<String, LoanApplyRequest>() {
                    @Override
                    public LoanApplyRequest apply(String state) throws Exception {
                        LoanApplyRequest request = new LoanApplyRequest();
                        request.setState(state);
                        return request;
                    }
                })
                .compose(new SatcatcheResponseTransformer<LoanApplyRequest, LoanApply>("loanApply"));
    }
}
