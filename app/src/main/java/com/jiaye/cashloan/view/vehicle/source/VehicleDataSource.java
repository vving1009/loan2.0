package com.jiaye.cashloan.view.vehicle.source;

import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.vehcile.CarPapersState;

import java.util.List;

import io.reactivex.Flowable;

/**
 * VehicleDataSource
 *
 * @author 贾博瑄
 */

public interface VehicleDataSource {
    Flowable<EmptyResponse> uploadFile(String folder, List<String> paths);

    Flowable<CarPapersState> submit();
}
