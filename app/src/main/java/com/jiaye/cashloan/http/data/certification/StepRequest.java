package com.jiaye.cashloan.http.data.certification;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildRequest;

/**
 * StepRequest
 *
 * @author 贾博瑄
 */
public class StepRequest extends SatcatcheChildRequest {

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
        return "";
    }
}
