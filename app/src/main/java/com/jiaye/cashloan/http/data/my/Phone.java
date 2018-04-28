package com.jiaye.cashloan.http.data.my;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildResponse;

/**
 * Phone
 *
 * @author 贾博瑄
 */

public class Phone extends SatcatcheChildResponse {

    @SerializedName("phone")
    private String phone;

    @SerializedName("operator")
    private String operator;

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
}
