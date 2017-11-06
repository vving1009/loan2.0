package com.jiaye.cashloan.http.base;

import com.google.gson.annotations.SerializedName;

/**
 * ChildResponse
 *
 * @author 贾博瑄
 */

public abstract class ChildResponse {

    /*流水号*/
    @SerializedName("req_no")
    private String serialnumber;

    /*令牌*/
    @SerializedName("jcb_id")
    private String token;

    /*设备唯一标示*/
    @SerializedName("app_key")
    private String deviceId;

    public String getSerialnumber() {
        return serialnumber;
    }

    public void setSerialnumber(String serialnumber) {
        this.serialnumber = serialnumber;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
