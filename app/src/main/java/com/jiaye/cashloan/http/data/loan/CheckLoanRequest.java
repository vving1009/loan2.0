package com.jiaye.cashloan.http.data.loan;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.ChildRequest;

/**
 * CheckLoanRequest
 *
 * @author 贾博瑄
 */

public class CheckLoanRequest extends ChildRequest {

    @SerializedName("jpd_id")
    private String productId;

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
