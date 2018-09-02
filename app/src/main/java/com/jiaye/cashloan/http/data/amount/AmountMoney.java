package com.jiaye.cashloan.http.data.amount;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildResponse;

/**
 * AmountMoney
 *
 * @author 贾博瑄
 */
public class AmountMoney extends SatcatcheChildResponse {

    @SerializedName("amount")
    private String amount;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
