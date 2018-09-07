package com.jiaye.cashloan.view.loancar.carprice.brand.source;

import com.jiaye.cashloan.http.data.car.CarBrand;

import io.reactivex.Flowable;

/**
 * BrandDataSource
 *
 * @author 贾博瑄
 */

public interface BrandDataSource {

    Flowable<CarBrand> getBrand();
}
