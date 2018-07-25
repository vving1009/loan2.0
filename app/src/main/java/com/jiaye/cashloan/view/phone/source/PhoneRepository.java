package com.jiaye.cashloan.view.phone.source;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.phone.UpdatePhoneRequest;
import com.jiaye.cashloan.http.utils.SatcatcheResponseTransformer;

import io.reactivex.Flowable;

/**
 * PhoneRepository
 *
 * @author 贾博瑄
 */

public class PhoneRepository implements PhoneDataSource {

    @Override
    public Flowable<EmptyResponse> requestUpdatePhone() {
        return Flowable.just(LoanApplication.getInstance().getDbHelper().queryUser())
                .map(user -> {
                    UpdatePhoneRequest request = new UpdatePhoneRequest();
                    request.setLoanId(user.getLoanId());
                    return request;
                }).compose(new SatcatcheResponseTransformer<UpdatePhoneRequest, EmptyResponse>("updatePhone"));
    }
}
