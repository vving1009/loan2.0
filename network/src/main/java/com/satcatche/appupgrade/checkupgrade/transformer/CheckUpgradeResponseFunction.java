package com.satcatche.appupgrade.checkupgrade.transformer;


import android.util.Log;

import com.satcatche.appupgrade.checkupgrade.bean.UpgradeResponse;
import com.satcatche.appupgrade.utils.ErrorCode;
import com.satcatche.appupgrade.utils.NetworkException;

import io.reactivex.functions.Function;

/**
 * CheckUpgradeResponseFunction
 *
 * @author 贾博瑄
 */
class CheckUpgradeResponseFunction implements Function<UpgradeResponse, UpgradeResponse.Body> {

    @Override
    public UpgradeResponse.Body apply(UpgradeResponse response) throws Exception {
        if (response.getReturnCode().equals(ErrorCode.SUCCESS.getCode())) {
            return response.getBody() == null ? emptyResponseBody() : response.getBody();
        } else {
            throw new NetworkException(response.getReturnCode(), response.getReturnMsg());
        }
    }

    private UpgradeResponse.Body emptyResponseBody() {
        UpgradeResponse.Body body = new UpgradeResponse.Body();
        body.setDownloadUrl("");
        body.setIsForce(0);
        body.setNotes("");
        body.setVersionCode(-1);
        body.setVersionName("");
        return body;
    }
}
