package com.jiaye.cashloan.http.data.auth;

import com.google.gson.annotations.SerializedName;

/**
 * UploadLoginRequest
 *
 * @author 贾博瑄
 */
public class UploadLoginRequest {

    @SerializedName("login_name")
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
