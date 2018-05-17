package com.jiaye.cashloan.http.data.auth.password;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildRequest;

/**
 * CheckForgetPasswordVerificationCodeRequest
 *
 * @author 贾博瑄
 */

public class CheckForgetPasswordVerificationCodeRequest extends SatcatcheChildRequest {

    /*手机号*/
    @SerializedName("phone")
    private String phone;

    /*短信验证码*/
    @SerializedName("sign_code")
    private String smsVerificationCode;

    /*验证请求来源, 修改密码=1, 忘记密码=2*/
    @SerializedName("sign_soure")
    private String source;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSmsVerificationCode() {
        return smsVerificationCode;
    }

    public void setSmsVerificationCode(String smsVerificationCode) {
        this.smsVerificationCode = smsVerificationCode;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    protected String getBusiness() {
        return "CL011";
    }
}
