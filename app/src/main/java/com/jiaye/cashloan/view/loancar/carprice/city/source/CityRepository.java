package com.jiaye.cashloan.view.loancar.carprice.city.source;

import com.jiaye.cashloan.BuildConfig;
import com.jiaye.cashloan.http.CarClient;
import com.jiaye.cashloan.http.data.car.CarCity;
import com.jiaye.cashloan.http.utils.CarFunction;

import java.util.List;

import io.reactivex.Flowable;

/**
 * CityRepository
 *
 * @author 贾博瑄
 */

public class CityRepository implements CityDataSource {

    @Override
    public Flowable<List<CarCity>> getCityList(String provinceId) {
        return CarClient.INSTANCE.getService()
                .getCarCity(BuildConfig.CAR_KEY, provinceId)
                .map(new CarFunction<>());
    }
}
