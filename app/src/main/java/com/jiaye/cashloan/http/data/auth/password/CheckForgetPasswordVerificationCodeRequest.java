package com.jiaye.cashloan.http.data.auth.password;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.ChildRequest;

/**
 * CheckForgetPasswordVerificationCodeRequest
 *
 * @author 贾博瑄
 */

public class CheckForgetPasswordVerificationCodeRequest extends ChildRequest {

    /*手机号*/
    @SerializedName("tel_phone")
    private String phone;

    /*短信验证码*/
    @SerializedName("sign_code")
    private String smsVerificationCode;

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

    @Override
    protected String getBusiness() {
        return "CL010";
    }
}
