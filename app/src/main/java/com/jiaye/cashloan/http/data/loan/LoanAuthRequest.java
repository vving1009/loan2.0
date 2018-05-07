package com.jiaye.cashloan.http.data.loan;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildRequest;

/**
 * LoanAuthRequest
 *
 * @author 贾博瑄
 */

public class LoanAuthRequest extends SatcatcheChildRequest {

    @SerializedName("tel_phone")
    private String phone;

    @SerializedName("jla_id")
    private String loanId;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    @Override
    protected String getBusiness() {
        return "CL006";
    }
}
