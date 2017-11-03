package com.jiaye.loan.cashloan.http.data.auth.password;

import com.google.gson.annotations.SerializedName;
import com.jiaye.loan.cashloan.http.base.ChildRequest;

/**
 * ChangePasswordRequest
 *
 * @author 贾博瑄
 */

public class ChangePasswordRequest extends ChildRequest {

    /*手机号*/
    @SerializedName("tel_phone")
    private String phone;

    /*密码*/
    @SerializedName("tel_content")
    private String password;

    /*类型*/
    @SerializedName("tel_status")
    private String status = "0";

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    protected String getBusiness() {
        return "CL004";
    }
}
