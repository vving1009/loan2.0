package com.jiaye.cashloan.view.loancar.carprice.series.source;

import com.jiaye.cashloan.BuildConfig;
import com.jiaye.cashloan.http.CarClient;
import com.jiaye.cashloan.http.data.car.CarSeries;
import com.jiaye.cashloan.http.utils.CarFunction;

import io.reactivex.Flowable;

/**
 * SeriesRepository
 *
 * @author 贾博瑄
 */

public class SeriesRepository implements SeriesDataSource {
    @Override
    public Flowable<CarSeries> getSeriesList(String brandId) {
        return CarClient.INSTANCE.getService()
                .getCarSeries(BuildConfig.CAR_KEY, brandId)
                .map(new CarFunction<>());
    }
}
