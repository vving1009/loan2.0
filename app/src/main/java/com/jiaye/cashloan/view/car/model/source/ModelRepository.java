package com.jiaye.cashloan.view.car.model.source;

import com.jiaye.cashloan.BuildConfig;
import com.jiaye.cashloan.http.CarClient;
import com.jiaye.cashloan.http.data.car.CarModel;
import com.jiaye.cashloan.http.utils.CarFunction;

import io.reactivex.Flowable;

/**
 * ModelRepository
 *
 * @author 贾博瑄
 */

public class ModelRepository implements ModelDataSource {

    @Override
    public Flowable<CarModel> getModelList(String seriesId) {
        return CarClient.INSTANCE.getService()
                .getCarModel(BuildConfig.CAR_KEY, seriesId)
                .map(new CarFunction<>());
    }
}
