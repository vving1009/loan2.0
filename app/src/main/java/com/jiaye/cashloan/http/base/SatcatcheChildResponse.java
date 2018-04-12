package com.jiaye.cashloan.http.base;

import com.google.gson.annotations.SerializedName;

/**
 * SatcatcheChildResponse
 *
 * @author 贾博瑄
 */
public abstract class SatcatcheChildResponse {

    /*流水号*/
    @SerializedName("req_no")
    private String serialnumber;

    public String getSerialnumber() {
        return serialnumber;
    }

    public void setSerialnumber(String serialnumber) {
        this.serialnumber = serialnumber;
    }
}
