package com.jiaye.cashloan.http.data.loan;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.ChildRequest;

/**
 * LoanVisaRequest
 *
 * @author 贾博瑄
 */

public class LoanVisaRequest extends ChildRequest {

    @SerializedName("app_jlaId")
    private String loanId;

    @SerializedName("app_opertype")
    private String type;

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    protected String getBusiness() {
        return "CL072";
    }
}
