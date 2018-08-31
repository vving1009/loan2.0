package com.jiaye.cashloan.view.car.brand.source;

import com.jiaye.cashloan.http.data.car.CarBrand;

import java.util.List;

import io.reactivex.Flowable;

/**
 * BrandDataSource
 *
 * @author 贾博瑄
 */

public interface BrandDataSource {

    Flowable<CarBrand> getBrand();
}
