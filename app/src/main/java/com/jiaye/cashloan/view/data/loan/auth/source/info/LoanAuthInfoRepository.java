package com.jiaye.cashloan.view.data.loan.auth.source.info;

import com.jiaye.cashloan.http.data.loan.LoanInfoAuth;
import com.jiaye.cashloan.http.data.loan.LoanInfoAuthRequest;
import com.jiaye.cashloan.http.utils.ResponseTransformer;

import io.reactivex.Flowable;

/**
 * LoanAuthInfoRepository
 *
 * @author 贾博瑄
 */

public class LoanAuthInfoRepository implements LoanAuthInfoDataSource {

    @Override
    public Flowable<LoanInfoAuth> requestLoanInfoAuth() {
        return Flowable.just(new LoanInfoAuthRequest())
                .compose(new ResponseTransformer<LoanInfoAuthRequest, LoanInfoAuth>("loanInfoAuth"));
    }
}
