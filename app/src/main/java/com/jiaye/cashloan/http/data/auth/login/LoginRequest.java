package com.jiaye.cashloan.http.data.auth.login;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.ChildRequest;

/**
 * LoginRequest
 *
 * @author 贾博瑄
 */

public class LoginRequest extends ChildRequest {

    /*手机号*/
    @SerializedName("phone")
    private String phone;

    /*密码*/
    @SerializedName("password")
    private String password;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    protected String getBusiness() {
        return "CL002";
    }
}
