package com.jiaye.cashloan.http.utils;


import com.jiaye.cashloan.http.base.ChildResponse;
import com.jiaye.cashloan.http.base.ErrorCode;
import com.jiaye.cashloan.http.base.NetworkException;
import com.jiaye.cashloan.http.base.Response;

import io.reactivex.functions.Function;

/**
 * ResponseFunction
 *
 * @author 贾博瑄
 */

public class ResponseFunction<T extends ChildResponse> implements Function<Response<T>, T> {

    @Override
    public T apply(Response<T> baseResponse) throws Exception {
        if (baseResponse.getContent().getHeader().getCode().equals(ErrorCode.SUCCESS.getCode())) {
            return baseResponse.getContent().getBody().get(0);
        } else {
            throw new NetworkException(baseResponse.getContent().getHeader().getCode(), baseResponse.getContent().getHeader().getMsg());
        }
    }
}
