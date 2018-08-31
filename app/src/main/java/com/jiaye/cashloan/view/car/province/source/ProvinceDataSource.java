package com.jiaye.cashloan.view.car.province.source;

import com.jiaye.cashloan.http.data.car.CarProvince;

import java.util.List;

import io.reactivex.Flowable;

/**
 * ProvinceDataSource
 *
 * @author 贾博瑄
 */

public interface ProvinceDataSource {

    Flowable<List<CarProvince>> getProvinceList();
}
