package com.jiaye.cashloan.http.data.loan;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildResponse;

/**
 * DefaultProduct
 *
 * @author 贾博瑄
 */

public class DefaultProduct extends SatcatcheChildResponse {

    /*产品编号*/
    @SerializedName("jpd_id")
    private String id;

    /*产品名称*/
    @SerializedName("jpd_name")
    private String name;

    /*借款金额*/
    @SerializedName("amount")
    private String amount;

    /*还款期限*/
    @SerializedName("deadline")
    private String deadline;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
}
