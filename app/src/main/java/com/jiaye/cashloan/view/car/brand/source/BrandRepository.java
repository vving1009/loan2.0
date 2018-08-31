package com.jiaye.cashloan.view.car.brand.source;

import com.jiaye.cashloan.BuildConfig;
import com.jiaye.cashloan.http.CarClient;
import com.jiaye.cashloan.http.data.car.CarBrand;
import com.jiaye.cashloan.http.utils.CarFunction;

import io.reactivex.Flowable;

/**
 * BrandRepository
 *
 * @author 贾博瑄
 */

public class BrandRepository implements BrandDataSource {

    @Override
    public Flowable<CarBrand> getBrand() {
        return CarClient.INSTANCE.getService()
                .getCarBrand(BuildConfig.CAR_KEY, "passenger")
                .map(new CarFunction<>());
    }
}
