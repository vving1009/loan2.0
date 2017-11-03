package com.jiaye.loan.cashloan.http.data.home;

import com.google.gson.annotations.SerializedName;
import com.jiaye.loan.cashloan.http.base.ChildRequest;

/**
 * ProductRequest
 *
 * @author 贾博瑄
 */

public class ProductRequest extends ChildRequest {

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
        return "CL005";
    }
}
