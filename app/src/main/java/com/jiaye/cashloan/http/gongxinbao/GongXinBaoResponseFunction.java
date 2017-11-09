package com.jiaye.cashloan.http.gongxinbao;

import com.jiaye.cashloan.http.base.NetworkException;

import io.reactivex.functions.Function;

/**
 * GongXinBaoResponseFunction
 *
 * @author 贾博瑄
 */

public class GongXinBaoResponseFunction<T> implements Function<GongXinBaoResponse<T>, T> {

    @Override
    public T apply(GongXinBaoResponse<T> baseResponse) throws Exception {
        if (baseResponse.getCode() == 1) {
            return baseResponse.getBody();
        } else {
            throw new NetworkException(String.valueOf(baseResponse.getCode()), baseResponse.getMessage());
        }
    }
}
