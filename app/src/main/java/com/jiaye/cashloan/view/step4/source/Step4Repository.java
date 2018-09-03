package com.jiaye.cashloan.view.step4.source;

import com.jiaye.cashloan.http.data.loan.LoanDate;
import com.jiaye.cashloan.http.data.loan.LoanDateRequest;
import com.jiaye.cashloan.http.utils.SatcatcheResponseTransformer;

import io.reactivex.Flowable;

/**
 * Step1ResultRepository
 *
 * @author 贾博瑄
 */

public class Step4Repository implements Step4DataSource {

    @Override
    public Flowable<LoanDate> requestLoanStatus() {
        return Flowable.just(new LoanDateRequest())
                .compose(new SatcatcheResponseTransformer<LoanDateRequest, LoanDate>("queryLoanDate"));
    }
}
