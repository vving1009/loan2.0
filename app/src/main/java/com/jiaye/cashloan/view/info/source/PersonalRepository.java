package com.jiaye.cashloan.view.info.source;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.data.loan.SavePerson;
import com.jiaye.cashloan.http.data.loan.SavePersonRequest;
import com.jiaye.cashloan.http.utils.SatcatcheResponseTransformer;

import io.reactivex.Flowable;

/**
 * PersonalRepository
 *
 * @author 贾博瑄
 */

public class PersonalRepository implements PersonalDataSource {

    @Override
    public Flowable<SavePerson> requestSavePerson(SavePersonRequest request) {
        return Flowable.just(request)
                .map(request1 -> {
                    String loanId = LoanApplication.getInstance().getDbHelper().queryUser().getLoanId();
                    request1.setLoanId(loanId);
                    return request1;
                })
                .compose(new SatcatcheResponseTransformer<SavePersonRequest, SavePerson>("savePerson"));
    }
}
