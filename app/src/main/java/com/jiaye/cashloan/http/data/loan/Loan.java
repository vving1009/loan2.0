package com.jiaye.cashloan.http.data.loan;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildResponse;

/**
 * Loan
 *
 * @author 贾博瑄
 */
public class Loan extends SatcatcheChildResponse {

    /*借款编号*/
    @SerializedName("jla_id")
    private String loanId;

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

}
