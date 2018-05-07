package com.jiaye.cashloan.http.data.loan;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildRequest;

/**
 * SaveTaoBaoRequest
 *
 * @author 贾博瑄
 */

public class SaveTaoBaoRequest extends SatcatcheChildRequest {

    @SerializedName("jla_id")
    private String loanId;

    @SerializedName("req_token")
    private String token;

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    protected String getBusiness() {
        return "CL019";
    }
}
