package com.jiaye.cashloan.http.utils;

import com.jiaye.cashloan.http.base.NetworkException;
import com.jiaye.cashloan.http.data.car.CarResponse;

import io.reactivex.functions.Function;

public class CarFunction<T> implements Function<CarResponse<T>, T> {

    @Override
    public T apply(CarResponse<T> baseCarResponse) throws Exception {
        if (baseCarResponse.getErrorCode() != 0) {
            throw new NetworkException(String.valueOf(baseCarResponse.getErrorCode()), baseCarResponse.getReason());
        }
        return baseCarResponse.getResult();
    }
}
