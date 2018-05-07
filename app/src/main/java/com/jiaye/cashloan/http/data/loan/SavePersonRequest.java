package com.jiaye.cashloan.http.data.loan;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildRequest;

/**
 * SavePersonRequest
 *
 * @author 贾博瑄
 */

public class SavePersonRequest extends SatcatcheChildRequest {

    /*借款编号*/
    @SerializedName("jla_id")
    private String loanId;

    /*最高学历编号*/
    @SerializedName("deu_typeid")
    private String education;

    /*最高学历*/
    @SerializedName("deu")
    private String educationValue;

    /*婚姻状况编号*/
    @SerializedName("marry_typeid")
    private String marriage;

    /*婚姻状况*/
    @SerializedName("marry")
    private String marriageValue;

    /*户籍所在城市*/
    @SerializedName("reg_city")
    private String registerCity;

    /*现居住城市*/
    @SerializedName("reside_city")
    private String city;

    /*现居住地址*/
    @SerializedName("reside_addr")
    private String address;

    /*电子邮箱*/
    @SerializedName("email")
    private String email;

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getEducationValue() {
        return educationValue;
    }

    public void setEducationValue(String educationValue) {
        this.educationValue = educationValue;
    }

    public String getMarriage() {
        return marriage;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }

    public String getMarriageValue() {
        return marriageValue;
    }

    public void setMarriageValue(String marriageValue) {
        this.marriageValue = marriageValue;
    }

    public String getRegisterCity() {
        return registerCity;
    }

    public void setRegisterCity(String registerCity) {
        this.registerCity = registerCity;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    protected String getBusiness() {
        return "CL009";
    }
}
