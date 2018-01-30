package com.jiaye.cashloan.http.tongdun;

import com.jiaye.cashloan.http.base.NetworkException;

import io.reactivex.functions.Function;


/**
 * TongDunOCRResponseFunction
 *
 * @author 贾博瑄
 */

public class TongDunOCRResponseFunction<T extends TongDunOCR> implements Function<TongDunOCRResponse<T>, T> {

    @Override
    public T apply(TongDunOCRResponse<T> baseResponse) throws Exception {
        if (baseResponse.isSuccess()) {
            if (baseResponse.getResult() == 0) {
                return baseResponse.getBody();
            } else {
                throw new NetworkException("", baseResponse.getBody().getMessage());
            }
        } else {
            throw new NetworkException(baseResponse.getReasonCode(), baseResponse.getReasonDesc());
        }
    }
}
