package com.jiaye.cashloan.http.data.home;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildRequest;

/**
 * LoanRequest
 *
 * @author 贾博瑄
 */
public class LoanRequest extends SatcatcheChildRequest {

    @SerializedName("tel_phone")
    private String phone;

    @SerializedName("jpd_id")
    private String productId = "0001";

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @Override
    protected String getBusiness() {
        return "CL085";
    }
}
