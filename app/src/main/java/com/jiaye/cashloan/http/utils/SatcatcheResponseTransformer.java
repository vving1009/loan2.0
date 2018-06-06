package com.jiaye.cashloan.http.utils;

import com.jiaye.cashloan.http.SatcatcheClient;
import com.jiaye.cashloan.http.SatcatcheService;
import com.jiaye.cashloan.http.base.SatcatcheChildRequest;
import com.jiaye.cashloan.http.base.SatcatcheChildResponse;
import com.jiaye.cashloan.http.base.SatcatcheRequest;
import com.jiaye.cashloan.http.base.SatcatcheResponse;

import org.reactivestreams.Publisher;

import java.lang.reflect.Method;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.functions.Function;

/**
 * SatcatcheResponseTransformer
 *
 * @author 贾博瑄
 */
public class SatcatcheResponseTransformer<Upstream extends SatcatcheChildRequest, Downstream extends SatcatcheChildResponse> implements FlowableTransformer<Upstream, Downstream> {

    private String mMethodName;

    public SatcatcheResponseTransformer(String methodName) {
        mMethodName = methodName;
    }

    @Override
    public Publisher<Downstream> apply(Flowable<Upstream> upstream) {
        return upstream.map(new SatcatcheRequestFunction<>())
                .flatMap((Function<SatcatcheRequest<Upstream>, Publisher<SatcatcheResponse<Downstream>>>) upstreamRequest -> {
                    SatcatcheService service = SatcatcheClient.INSTANCE.getService();
                    Method method = service.getClass().getMethod(mMethodName, upstreamRequest.getClass());
                    //noinspection unchecked
                    return (Publisher<SatcatcheResponse<Downstream>>) method.invoke(service, upstreamRequest);
                })
                .map(new SatcatcheResponseFunction<>());
    }
}
