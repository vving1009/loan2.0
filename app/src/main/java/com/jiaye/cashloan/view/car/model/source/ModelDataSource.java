package com.jiaye.cashloan.view.car.model.source;

import com.jiaye.cashloan.http.data.car.CarModel;

import java.util.List;

import io.reactivex.Flowable;

/**
 * ModelDataSource
 *
 * @author 贾博瑄
 */

public interface ModelDataSource {

    Flowable<CarModel> getModelList(String seriesId);
}
