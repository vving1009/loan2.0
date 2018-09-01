package com.jiaye.cashloan.view.step1.source;

import com.jiaye.cashloan.BuildConfig;
import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.CarClient;
import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.car.CarPrice;
import com.jiaye.cashloan.http.data.car.SaveValuationRequest;
import com.jiaye.cashloan.http.data.certification.UpdateStepRequest;
import com.jiaye.cashloan.http.utils.CarFunction;
import com.jiaye.cashloan.http.utils.SatcatcheResponseTransformer;

import io.reactivex.Flowable;

/**
 * Step1Repository
 *
 * @author 贾博瑄
 */

public class Step1Repository implements Step1DataSource {

    @Override
    public Flowable<CarPrice> requestCarPrice(Step1InputState step1) {
        return CarClient.INSTANCE.getService().getCarPrice(BuildConfig.CAR_KEY, "1", "1",
                step1.getCarId(), step1.getCarProvince(), step1.getCarCity(), step1.getCarYear(),
                step1.getCarMonth(), step1.getCarMiles())
                .map(new CarFunction<>());
    }

    @Override
    public Flowable<EmptyResponse> saveCarPrice(String value) {
        return Flowable.just(LoanApplication.getInstance().getDbHelper().queryUser())
                .map(user -> {
                    SaveValuationRequest request = new SaveValuationRequest();
                    request.setJlaId(user.getLoanId());
                    request.setValuation(value);
                    return request;
                })
                .compose(new SatcatcheResponseTransformer<SaveValuationRequest, EmptyResponse>("saveCarPrice"));
    }

    @Override
    public Flowable<EmptyResponse> requestUpdateStep() {
        return Flowable.just(LoanApplication.getInstance().getDbHelper().queryUser())
                .map(user -> {
                    UpdateStepRequest request = new UpdateStepRequest();
                    request.setLoanId(user.getLoanId());
                    request.setStatus(4);
                    request.setMsg("审核通过");
                    return request;
                })
                .compose(new SatcatcheResponseTransformer<UpdateStepRequest, EmptyResponse>("updateStep"));
    }
}
