package com.satcatche.appupgrade.checkupgrade.transformer;

import com.satcatche.appupgrade.checkupgrade.CheckUpgradeClient;
import com.satcatche.appupgrade.checkupgrade.bean.UpgradeRequest;
import com.satcatche.appupgrade.checkupgrade.bean.UpgradeResponse;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.functions.Function;

/**
 * CheckUpgradeTransformer
 *
 * @author 贾博瑄
 */
public class CheckUpgradeTransformer implements FlowableTransformer<UpgradeRequest.Body, UpgradeResponse.Body> {

    @Override
    public Publisher<UpgradeResponse.Body> apply(Flowable<UpgradeRequest.Body> upstream) {
        return upstream.map(new CheckUpgradeRequestFunction())
                .flatMap((Function<UpgradeRequest, Publisher<UpgradeResponse>>) request ->
                    CheckUpgradeClient.INSTANCE.getService().checkUpdate(request))
                .map(new CheckUpgradeResponseFunction());
    }
}
