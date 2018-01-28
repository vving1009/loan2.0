package com.jiaye.cashloan.http.data.my;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.ChildRequest;

/**
 * CreditCashRequest
 *
 * @author 贾博瑄
 */

public class CreditCashRequest extends ChildRequest {

    @SerializedName("tx_amount")
    private String cash;

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    @Override
    protected String getBusiness() {
        return "CL056";
    }
}
