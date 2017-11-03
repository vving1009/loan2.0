package com.jiaye.loan.cashloan.http.data.auth.register;

import com.google.gson.annotations.SerializedName;
import com.jiaye.loan.cashloan.http.base.ChildRequest;

/**
 * RegisterRequest
 *
 * @author 贾博瑄
 */

public class RegisterRequest extends ChildRequest {

    /*手机号*/
    @SerializedName("tel_phone")
    private String phone;

    /*图片验证码*/
    @SerializedName("img_code")
    private String imgVerificationCode;

    /*密码*/
    @SerializedName("tel_content")
    private String password;

    /*短信验证码*/
    @SerializedName("verification_code")
    private String smsVerificationCode;

    /*推荐码(可选)*/
    @SerializedName("referral_code")
    private String referralCode;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImgVerificationCode() {
        return imgVerificationCode;
    }

    public void setImgVerificationCode(String imgVerificationCode) {
        this.imgVerificationCode = imgVerificationCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSmsVerificationCode() {
        return smsVerificationCode;
    }

    public void setSmsVerificationCode(String smsVerificationCode) {
        this.smsVerificationCode = smsVerificationCode;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    @Override
    protected String getBusiness() {
        return "CL001";
    }
}
