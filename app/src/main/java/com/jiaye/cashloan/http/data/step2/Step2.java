package com.jiaye.cashloan.http.data.step2;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildResponse;

/**
 * Step2
 *
 * @author 贾博瑄
 */
public class Step2 extends SatcatcheChildResponse {

    @SerializedName("amount")
    private String amount;

    @SerializedName("illustrate")
    private String msg;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
