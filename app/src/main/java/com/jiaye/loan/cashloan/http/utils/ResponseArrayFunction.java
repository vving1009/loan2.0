package com.jiaye.loan.cashloan.http.utils;

import com.jiaye.loan.cashloan.http.base.ErrorCode;
import com.jiaye.loan.cashloan.http.base.NetworkException;
import com.jiaye.loan.cashloan.http.base.Response;

import java.util.List;

import io.reactivex.functions.Function;

/**
 * ResponseArrayFunction
 *
 * @author 贾博瑄
 */

public class ResponseArrayFunction<T> implements Function<Response<T>, List<T>> {

    @Override
    public List<T> apply(Response<T> baseResponse) throws Exception {
        if (baseResponse.getContent().getHeader().getCode().equals(ErrorCode.SUCCESS.getCode())) {
            return baseResponse.getContent().getBody();
        } else {
            throw new NetworkException(baseResponse.getContent().getHeader().getCode(), baseResponse.getContent().getHeader().getMsg());
        }
    }
}
