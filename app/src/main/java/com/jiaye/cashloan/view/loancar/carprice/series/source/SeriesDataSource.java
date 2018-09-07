package com.jiaye.cashloan.view.loancar.carprice.series.source;

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
