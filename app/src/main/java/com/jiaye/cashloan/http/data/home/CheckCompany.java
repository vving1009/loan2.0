package com.jiaye.cashloan.http.data.home;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildResponse;

/**
 * CheckCompany
 *
 * @author 贾博瑄
 */
public class CheckCompany extends SatcatcheChildResponse {

    @SerializedName("user_source")
    private boolean isNeed;

    public boolean isNeed() {
        return isNeed;
    }

    public void setNeed(boolean need) {
        isNeed = need;
    }
}
