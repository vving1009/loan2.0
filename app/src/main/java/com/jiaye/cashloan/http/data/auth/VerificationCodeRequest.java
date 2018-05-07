package com.jiaye.cashloan.http.data.auth;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildRequest;

/**
 * VerificationCodeRequest
 *
 * @author 贾博瑄
 */

public class VerificationCodeRequest extends SatcatcheChildRequest {

    /*手机号*/
    @SerializedName("phone")
    private String phone;

    /*类型 0 注册页面获取验证码 1 修改密码页面获取验证码 2 忘记密码页面获取验证码*/
    @SerializedName("status")
    private String status;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    protected String getBusiness() {
        return "CL003";
    }
}
