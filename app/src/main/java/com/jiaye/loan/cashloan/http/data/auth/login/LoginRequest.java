package com.jiaye.loan.cashloan.http.data.auth.login;

import com.google.gson.annotations.SerializedName;
import com.jiaye.loan.cashloan.http.base.ChildRequest;

/**
 * LoginRequest
 *
 * @author 贾博瑄
 */

public class LoginRequest extends ChildRequest {

    /*手机号*/
    @SerializedName("tel_phone")
    private String phone;

    /*密码*/
    @SerializedName("tel_content")
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
        return "DL001";
    }
}
