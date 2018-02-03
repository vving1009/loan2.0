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

    @SerializedName("bank_cnapsNo")
    private String bank;

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    @Override
    protected String getBusiness() {
        return "CL056";
    }
}
