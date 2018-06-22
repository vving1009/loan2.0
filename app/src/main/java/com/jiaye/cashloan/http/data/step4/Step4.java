package com.jiaye.cashloan.http.data.step4;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildResponse;

/**
 * Step4
 *
 * @author 贾博瑄
 */
public class Step4 extends SatcatcheChildResponse {

    @SerializedName("amount")
    private String amount;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
