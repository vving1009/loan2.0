package com.jiaye.cashloan.view.loancar.carprice.province.source;

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
