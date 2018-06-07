package com.jiaye.cashloan.http.data.login;

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

    /*类型 验证码登录 3*/
    @SerializedName("status")
    private int status = 3;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    protected String getBusiness() {
        return "CL003";
    }
}
