package com.jiaye.cashloan.view.loancar.carprice.year.source;

import com.jiaye.cashloan.BuildConfig;
import com.jiaye.cashloan.http.CarClient;
import com.jiaye.cashloan.http.utils.CarFunction;

import java.util.List;

import io.reactivex.Flowable;

/**
 * YearRepository
 *
 * @author 贾博瑄
 */

public class YearRepository implements YearDataSource {

    @Override
    public Flowable<List<String>> getYearList(String modelId) {
        return CarClient.INSTANCE.getService()
                .getCarSalesYear(BuildConfig.CAR_KEY, modelId)
                .map(new CarFunction<>());
    }
}
