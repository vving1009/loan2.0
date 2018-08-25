package com.jiaye.cashloan.view.jdcar.brand.source;

import com.jiaye.cashloan.BuildConfig;
import com.jiaye.cashloan.http.JdClient;
import com.jiaye.cashloan.http.data.jdcar.JdCarBrand;
import com.jiaye.cashloan.http.utils.JdFunctionList;

import java.util.List;

import io.reactivex.Flowable;

/**
 * BrandRepository
 *
 * @author 贾博�?
 */

public class BrandRepository implements BrandDataSource {

    @Override
    public Flowable<List<JdCarBrand>> getBrandList() {
        return JdClient.INSTANCE.getService()
                .getCarBrand(BuildConfig.JD_CAR_KEY)
                .map(new JdFunctionList<>());
    }
}
