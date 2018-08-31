package com.jiaye.cashloan.view.car.series.source;

import com.jiaye.cashloan.http.data.car.CarSeries;

import io.reactivex.Flowable;

/**
 * SeriesDataSource
 *
 * @author 贾博瑄
 */

public interface SeriesDataSource {

    Flowable<CarSeries> getSeriesList(String brandId);
}
