package com.jiaye.cashloan.view.info2.source;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.certification.UpdateStepRequest;
import com.jiaye.cashloan.http.data.saveauth.SaveAuthRequest;
import com.jiaye.cashloan.http.utils.SatcatcheResponseTransformer;

import io.reactivex.Flowable;

/**
 * Info2Repository
 *
 * @author 贾博瑄
 */

public class Info2Repository implements Info2DataSource {

    @Override
    public Flowable<EmptyResponse> submit(SaveAuthRequest request) {
        request.setJlaId(LoanApplication.getInstance().getDbHelper().queryUser().getLoanId());
        return Flowable.just(request)
                .compose(new SatcatcheResponseTransformer<SaveAuthRequest, EmptyResponse>("saveAuthInfo"));
    }

    @Override
    public Flowable<EmptyResponse> requestUpdateStep() {
        return Flowable.just(LoanApplication.getInstance().getDbHelper().queryUser())
                .map(user -> {
                    UpdateStepRequest request = new UpdateStepRequest();
                    request.setLoanId(user.getLoanId());
                    request.setStatus(10);
                    request.setMsg("去开户");
                    return request;
                })
                .compose(new SatcatcheResponseTransformer<UpdateStepRequest, EmptyResponse>("updateStep"));
    }
}
