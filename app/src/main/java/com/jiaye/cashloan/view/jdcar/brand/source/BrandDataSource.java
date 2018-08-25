package com.jiaye.cashloan.view.jdcar.brand.source;

import com.jiaye.cashloan.http.data.jdcar.JdCarBrand;

import java.util.List;

import io.reactivex.Flowable;

/**
 * BrandDataSource
 *
 * @author 贾博�?
 */

public interface BrandDataSource {

    Flowable<List<JdCarBrand>> getBrandList();
}
