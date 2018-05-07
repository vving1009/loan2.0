package com.jiaye.cashloan.http.data.launch;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.BuildConfig;
import com.jiaye.cashloan.http.base.SatcatcheChildRequest;

/**
 * CheckUpdateRequest
 *
 * @author 贾博瑄
 */

public class CheckUpdateRequest extends SatcatcheChildRequest {

    @SerializedName("versionCode")
    private int code = BuildConfig.VERSION_CODE;

    @SerializedName("appPackage")
    private String packageName = BuildConfig.APPLICATION_ID;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @Override
    protected String getBusiness() {
        return "CL072";
    }
}
