package com.jiaye.cashloan.view.loancar.carprice.city.source;

import com.jiaye.cashloan.http.data.car.CarCity;

import java.util.List;

import io.reactivex.Flowable;

/**
 * CityDataSource
 *
 * @author 贾博瑄
 */

public interface CityDataSource {

    Flowable<List<CarCity>> getCityList(String provinceId);
}
