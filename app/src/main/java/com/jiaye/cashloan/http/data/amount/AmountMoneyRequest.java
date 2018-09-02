package com.jiaye.cashloan.http.data.amount;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildRequest;

/**
 * AmountMoneyRequest
 *
 * @author 贾博瑄
 */
public class AmountMoneyRequest extends SatcatcheChildRequest {

    @SerializedName("jla_id")
    private String loanId;

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    @Override
    protected String getBusiness() {
        return "CL026";
    }
}
