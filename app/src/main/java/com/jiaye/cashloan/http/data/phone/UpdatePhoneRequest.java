package com.jiaye.cashloan.http.data.phone;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildRequest;

/**
 * UpdatePhoneRequest
 *
 * @author 贾博瑄
 */
public class UpdatePhoneRequest extends SatcatcheChildRequest {

    @SerializedName("userinfo_id")
    private String token;

    @SerializedName("jla_id")
    private String loanId;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    @Override
    protected String getBusiness() {
        return "CL031";
    }
}
