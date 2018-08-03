package com.jiaye.cashloan.http.utils;

import com.jiaye.cashloan.http.base.SatcatcheChildRequest;
import com.jiaye.cashloan.http.base.SatcatcheRequest;
import com.jiaye.cashloan.http.base.SatcatcheRequestContent;
import com.jiaye.cashloan.http.base.SatcatcheRequestHeader;

import io.reactivex.functions.Function;

/**
 * SatcatcheRequestFunction
 *
 * @author 贾博瑄
 */
public class SatcatcheRequestFunction<T extends SatcatcheChildRequest> implements Function<T, SatcatcheRequest<T>> {

    @Override
    public SatcatcheRequest<T> apply(T t) throws Exception {
        SatcatcheRequest<T> request = new SatcatcheRequest<>();
        SatcatcheRequestContent<T> content = new SatcatcheRequestContent<>();
        content.setHeader(SatcatcheRequestHeader.create(t.getSerialnumber()));
        content.setBody(t);
        request.setContent(content);
        return request;
    }
}
