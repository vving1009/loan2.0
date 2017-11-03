package com.jiaye.loan.cashloan.http.data.auth.register;

import com.google.gson.annotations.SerializedName;
import com.jiaye.loan.cashloan.http.data.auth.VerificationCodeRequest;

/**
 * RegisterVerificationCodeRequest
 *
 * @author 贾博瑄
 */

public class RegisterVerificationCodeRequest extends VerificationCodeRequest {

    /*手机号*/
    @SerializedName("tel_phone")
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    protected String getBusiness() {
        return "DX001";
    }

    @Override
    protected String getStatus() {
        return "0";
    }
}
