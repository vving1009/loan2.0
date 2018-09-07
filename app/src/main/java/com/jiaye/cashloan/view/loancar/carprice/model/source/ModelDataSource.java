package com.jiaye.cashloan.view.loancar.carprice.model.source;

import com.jiaye.cashloan.http.data.car.CarModel;

import io.reactivex.Flowable;

/**
 * ModelDataSource
 *
 * @author 贾博瑄
 */

public interface ModelDataSource {

    Flowable<CarModel> getModelList(String seriesId);
}
