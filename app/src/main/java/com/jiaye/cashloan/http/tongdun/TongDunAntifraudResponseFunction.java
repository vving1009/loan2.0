package com.jiaye.cashloan.http.tongdun;

import android.text.TextUtils;

import com.jiaye.cashloan.http.base.NetworkException;

import io.reactivex.functions.Function;

/**
 * TongDunAntifraudResponseFunction
 *
 * @author 贾博瑄
 */

public class TongDunAntifraudResponseFunction<T> implements Function<TongDunAntifraudResponse<TongDunAntifraud<T>>, T> {

    @Override
    public T apply(TongDunAntifraudResponse<TongDunAntifraud<T>> baseResponse) throws Exception {
        if (baseResponse.isSuccess()) {
            if (TextUtils.isEmpty(baseResponse.getReasonCode())) {
                return baseResponse.getBody().getAntifraud();
            } else {
                throw new NetworkException(baseResponse.getReasonCode(), baseResponse.getReasonDesc());
            }
        } else {
            throw new NetworkException(baseResponse.getReasonCode(), baseResponse.getReasonDesc());
        }
    }
}
