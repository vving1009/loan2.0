package com.jiaye.cashloan.http.tongdun;

import com.jiaye.cashloan.http.base.NetworkException;

import io.reactivex.functions.Function;


/**
 * TongDunResponseFunction
 *
 * @author 贾博瑄
 */

public class TongDunResponseFunction<T> implements Function<TongDunResponse<T>, T> {

    @Override
    public T apply(TongDunResponse<T> baseResponse) throws Exception {
        if (baseResponse.isSuccess()) {
            if (baseResponse.getResult() == 0) {
                return baseResponse.getBody();
            } else {
                return null;
            }
        } else {
            throw new NetworkException(baseResponse.getReasonCode(), baseResponse.getReasonDesc());
        }
    }
}
