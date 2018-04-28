package com.jiaye.cashloan.http.utils;

import com.jiaye.cashloan.http.LoanClient;
import com.jiaye.cashloan.http.LoanService;
import com.jiaye.cashloan.http.base.ChildResponse;
import com.jiaye.cashloan.http.base.Request;
import com.jiaye.cashloan.http.base.Response;

import org.reactivestreams.Publisher;

import java.lang.reflect.Method;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.functions.Function;

/**
 * ResponseTransformer
 *
 * @author 贾博瑄
 */

public class ResponseTransformer<Upstream, Downstream extends ChildResponse> implements FlowableTransformer<Upstream, Downstream> {

    private String mMethodName;

    public ResponseTransformer(String methodName) {
        mMethodName = methodName;
    }

    @Override
    public Publisher<Downstream> apply(Flowable<Upstream> upstream) {
        return upstream.map(new RequestFunction<Upstream>())
                .flatMap(new Function<Request<Upstream>, Publisher<Response<Downstream>>>() {
                    @Override
                    public Publisher<Response<Downstream>> apply(Request<Upstream> upstreamRequest) throws Exception {
                        LoanService loanService = LoanClient.INSTANCE.getService();
                        Method method = loanService.getClass().getMethod(mMethodName, upstreamRequest.getClass());
                        //noinspection unchecked
                        return (Publisher<Response<Downstream>>) method.invoke(loanService, upstreamRequest);
                    }
                })
                .map(new ResponseFunction<Downstream>());
    }
}
