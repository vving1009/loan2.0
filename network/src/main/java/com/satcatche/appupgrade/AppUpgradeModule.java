package com.satcatche.appupgrade;

import com.satcatche.appupgrade.checkupgrade.bean.UpgradeRequest;
import com.satcatche.appupgrade.checkupgrade.bean.UpgradeResponse;
import com.satcatche.appupgrade.checkupgrade.transformer.CheckUpgradeTransformer;
import com.satcatche.appupgrade.utils.UpgradeConfig;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

public class AppUpgradeModule {

    public static Flowable<UpgradeResponse.Body> getCheckUpgradeFlowable() {
        UpgradeRequest.Body requestBody = new UpgradeRequest.Body();
        requestBody.setAppPackage(UpgradeConfig.getPackageName());
        requestBody.setVersionCode(UpgradeConfig.getVersionCode());
        return Flowable.just(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .compose(new CheckUpgradeTransformer());
    }
}
