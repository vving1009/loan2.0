package com.jiaye.cashloan.http.data.loan;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildRequest;

/**
 * UploadLocationRequest
 *
 * @author 贾博瑄
 */

public class UploadLocationRequest extends SatcatcheChildRequest {

    @SerializedName("longitude")
    private String longitude;

    @SerializedName("latitude")
    private String latitude;

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Override
    protected String getBusiness() {
        return "CLO89";
    }
}
