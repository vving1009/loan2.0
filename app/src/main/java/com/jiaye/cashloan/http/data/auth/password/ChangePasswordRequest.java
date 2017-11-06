package com.jiaye.cashloan.http.data.auth.password;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.ChildRequest;

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

    /*类型 0 用户登录的情况下 1 用户未登录的情况下 */
    @SerializedName("tel_status")
    private String status;

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
