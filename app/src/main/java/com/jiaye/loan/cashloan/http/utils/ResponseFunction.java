package com.jiaye.loan.cashloan.http.utils;


import com.jiaye.loan.cashloan.http.base.ErrorCode;
import com.jiaye.loan.cashloan.http.base.NetworkException;
import com.jiaye.loan.cashloan.http.base.Response;

import io.reactivex.functions.Function;

/**
 * ResponseFunction
 *
 * @author 贾博瑄
 */

public class ResponseFunction<T> implements Function<Response<T>, T> {

    @Override
    public T apply(Response<T> baseResponse) throws Exception {
        if (baseResponse.getContent().getHeader().getCode().equals(ErrorCode.SUCCESS.getCode())) {
            return baseResponse.getContent().getBody().get(0);
        } else {
            throw new NetworkException(baseResponse.getContent().getHeader().getCode(), baseResponse.getContent().getHeader().getMsg());
        }
    }
}
