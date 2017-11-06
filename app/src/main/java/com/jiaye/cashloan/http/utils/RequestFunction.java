package com.jiaye.cashloan.http.utils;

import com.jiaye.cashloan.http.base.Request;
import com.jiaye.cashloan.http.base.RequestContent;
import com.jiaye.cashloan.http.base.RequestHeader;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Function;

/**
 * RequestFunction
 *
 * @author 贾博瑄
 */

public class RequestFunction<T> implements Function<T, Request<T>> {

    @Override
    public Request<T> apply(T t) throws Exception {
        Request<T> request = new Request<>();
        RequestContent<T> content = new RequestContent<>();
        content.setHeader(RequestHeader.create());
        List<T> list = new ArrayList<>();
        list.add(t);
        content.setBody(list);
        request.setContent(content);
        return request;
    }
}
