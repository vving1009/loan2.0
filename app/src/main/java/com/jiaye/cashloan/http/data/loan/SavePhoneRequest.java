package com.jiaye.cashloan.http.data.loan;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.ChildRequest;

/**
 * SavePhoneRequest
 *
 * @author 贾博瑄
 */

public class SavePhoneRequest extends ChildRequest {

    @SerializedName("req_token")
    private String token;

    @SerializedName("phone")
    private String phone;

    @SerializedName("operator")
    private String operator;

    @SerializedName("ser_code")
    private String password;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    protected String getBusiness() {
        return "CL026";
    }
}
