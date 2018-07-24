package com.jiaye.cashloan.http.register;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildRequest;

/**
 * RegisterRequest
 *
 * @author 贾博瑄
 */
public class RegisterRequest extends SatcatcheChildRequest {

    @SerializedName("phone")
    private String phone;

    @SerializedName("sign_code")
    private String code;

    @SerializedName("password")
    private String password;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    protected String getBusiness() {
        return "CL0027";
    }
}
