package com.satcatche.appupgrade.checkupgrade.transformer;


import com.satcatche.appupgrade.checkupgrade.bean.UpgradeRequest;

import io.reactivex.functions.Function;

/**
 * CheckUpgradeRequestFunction
 *
 * @author 贾博瑄
 */
public class CheckUpgradeRequestFunction implements Function<UpgradeRequest.Body, UpgradeRequest> {

    @Override
    public UpgradeRequest apply(UpgradeRequest.Body body) throws Exception {
        UpgradeRequest request = new UpgradeRequest();
        request.setBody(body);
        return request;
    }
}
