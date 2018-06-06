package com.jiaye.cashloan.http.utils;

import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.base.ErrorCode;
import com.jiaye.cashloan.http.base.NetworkException;
import com.jiaye.cashloan.http.base.SatcatcheChildResponse;
import com.jiaye.cashloan.http.base.SatcatcheResponse;

import io.reactivex.functions.Function;

/**
 * SatcatcheResponseFunction
 *
 * @author 贾博瑄
 */
public class SatcatcheResponseFunction<T extends SatcatcheChildResponse> implements Function<SatcatcheResponse<T>, T> {

    @Override
    public T apply(SatcatcheResponse<T> baseResponse) throws Exception {
        if (baseResponse.getContent().getCode().equals(ErrorCode.SUCCESS.getCode())) {
            if (baseResponse.getContent().getBody() != null) {
                return baseResponse.getContent().getBody();
            } else {
                return (T) new EmptyResponse();
            }
        } else {
            throw new NetworkException(baseResponse.getContent().getCode(), baseResponse.getContent().getMsg());
        }
    }
}
