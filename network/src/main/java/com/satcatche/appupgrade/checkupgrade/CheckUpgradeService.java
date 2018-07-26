package com.satcatche.appupgrade.checkupgrade;

import com.satcatche.appupgrade.checkupgrade.bean.UpgradeRequest;
import com.satcatche.appupgrade.checkupgrade.bean.UpgradeResponse;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * CheckUpgradeService
 *
 * @author 贾博瑄
 */
public interface CheckUpgradeService {
    /**
     * 检测升级
     */
    @POST("getVersion")
    Flowable<UpgradeResponse> checkUpdate(@Body UpgradeRequest request);
}

