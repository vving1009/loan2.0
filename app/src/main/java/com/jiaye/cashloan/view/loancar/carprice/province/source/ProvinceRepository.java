package com.jiaye.cashloan.view.loancar.carprice.province.source;

import com.jiaye.cashloan.BuildConfig;
import com.jiaye.cashloan.http.CarClient;
import com.jiaye.cashloan.http.data.car.CarProvince;
import com.jiaye.cashloan.http.utils.CarFunction;

import java.util.List;

import io.reactivex.Flowable;

/**
 * ProvinceRepository
 *
 * @author 贾博瑄
 */

public class ProvinceRepository implements ProvinceDataSource {
    @Override
    public Flowable<List<CarProvince>> getProvinceList() {
        return CarClient.INSTANCE.getService()
                .getCarProvince(BuildConfig.CAR_KEY)
                .map(new CarFunction<>());
    }
}
