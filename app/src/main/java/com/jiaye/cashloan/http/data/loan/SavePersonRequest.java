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
