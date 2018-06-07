package com.jiaye.cashloan.http.data.home;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildRequest;

/**
 * CheckLoanRequest
 *
 * @author 贾博瑄
 */

public class CheckLoanRequest extends SatcatcheChildRequest {

    @SerializedName("jpd_id")
    private String productId = "0001";

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @Override
    protected String getBusiness() {
        return "CL043";
    }
}
