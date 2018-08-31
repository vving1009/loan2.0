package com.jiaye.cashloan.view.step1.input.source;

import com.jiaye.cashloan.BuildConfig;
import com.jiaye.cashloan.http.CarClient;
import com.jiaye.cashloan.http.data.car.CarPrice;
import com.jiaye.cashloan.http.utils.CarFunction;
import com.jiaye.cashloan.view.step1.input.Step1InputState;

import io.reactivex.Flowable;

/**
 * Step1InputRepository
 *
 * @author 贾博瑄
 */

public class Step1InputRepository implements Step1InputDataSource {

    /*@Override
    public Flowable<Step2Input> requestStep1() {
        return Flowable.just(LoanApplication.getInstance().getDbHelper().queryUser())
                .map(user -> {
                    Step2InputRequest request = new Step2InputRequest();
                    request.setLoanId(user.getLoanId());
                    return request;
                })
                .compose(new SatcatcheResponseTransformer<Step2InputRequest, Step2Input>("step1"));
    }

    @Override
    public Flowable<EmptyResponse> requestUpdateStep() {
        return Flowable.just(LoanApplication.getInstance().getDbHelper().queryUser())
                .map(user -> {
                    UpdateStepRequest request = new UpdateStepRequest();
                    request.setLoanId(user.getLoanId());
                    request.setStatus(2);
                    request.setMsg("未审核");
                    return request;
                })
                .compose(new SatcatcheResponseTransformer<UpdateStepRequest, EmptyResponse>("updateStep"));
    }*/

    @Override
    public Flowable<CarPrice> requestCarPrice(Step1InputState step1) {
        return CarClient.INSTANCE.getService().getCarPrice(BuildConfig.CAR_KEY, "1", "1",
                step1.getCarId(), step1.getCarProvince(), step1.getCarCity(), step1.getCarYear(),
                step1.getCarMonth(), step1.getCarMiles())
                .map(new CarFunction<>());
    }
}
