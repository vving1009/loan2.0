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
    private String serialNumber;

    /*令牌*/
    @SerializedName("userinfo_id")
    private String token;

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
