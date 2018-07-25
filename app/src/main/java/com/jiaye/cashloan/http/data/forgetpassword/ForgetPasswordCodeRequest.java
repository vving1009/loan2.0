package com.jiaye.cashloan.http.data.forgetpassword;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildRequest;

/**
 * ForgetPasswordCodeRequest
 *
 * @author 贾博瑄
 */
public class ForgetPasswordCodeRequest extends SatcatcheChildRequest {

    /*手机号*/
    @SerializedName("phone")
    private String phone;

    /*类型 注册 0 忘记密码 1*/
    @SerializedName("status")
    private int status;

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
        return "CL029";
    }
}
