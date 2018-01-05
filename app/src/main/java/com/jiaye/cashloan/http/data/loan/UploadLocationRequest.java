package com.jiaye.cashloan.http.data.loan;

import com.google.gson.annotations.SerializedName;

/**
 * UploadLocationRequest
 *
 * @author 贾博瑄
 */

public class UploadLocationRequest {

    @SerializedName("login_name")
    private String phone;

    @SerializedName("longitude")
    private String longitude;

    @SerializedName("latitude")
    private String latitude;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

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
}
