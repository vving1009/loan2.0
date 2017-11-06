package com.jiaye.cashloan.http.base;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.utils.DateUtil;
import com.jiaye.cashloan.utils.RandomUtil;

/**
 * ChildRequest
 *
 * @author 贾博瑄
 */

public abstract class ChildRequest {

    /*流水号*/
    @SerializedName("req_no")
    private String serialnumber = getBusiness() + DateUtil.formatDateTime(System.currentTimeMillis()) + RandomUtil.number4();

    public String getSerialnumber() {
        return serialnumber;
    }

    protected abstract String getBusiness();
}
