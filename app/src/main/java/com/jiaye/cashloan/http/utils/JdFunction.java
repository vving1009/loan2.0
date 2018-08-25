package com.jiaye.cashloan.http.utils;

import com.jiaye.cashloan.http.base.NetworkException;
import com.jiaye.cashloan.http.data.jdcar.JdResponse;

import java.util.List;

import io.reactivex.functions.Function;

public class JdFunction<T> implements Function<JdResponse<T>, T> {

    @Override
    public T apply(JdResponse<T> baseResponse) throws Exception {
        if (!baseResponse.getCode().equals("10000")) {
            throw new NetworkException(baseResponse.getCode(), baseResponse.getMsg());
        }
        if (!baseResponse.getResult().getCode().equals("1000")) {
            throw new NetworkException(baseResponse.getResult().getCode(), baseResponse.getResult().getMessage());
        }
        if (!baseResponse.getResult().getResultInner().getCode().equals("200")) {
            throw new NetworkException(baseResponse.getResult().getResultInner().getCode(), baseResponse.getResult().getResultInner().getMessage());
        }
        return baseResponse.getResult().getResultInner().getBody();
    }
}
