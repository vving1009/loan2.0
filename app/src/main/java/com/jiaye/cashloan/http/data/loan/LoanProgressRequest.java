package com.jiaye.cashloan.http.data.loan;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildRequest;

/**
 * LoanProgressRequest
 *
 * @author 贾博瑄
 */

public class LoanProgressRequest extends SatcatcheChildRequest {

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
        return "CL028";
    }
}
