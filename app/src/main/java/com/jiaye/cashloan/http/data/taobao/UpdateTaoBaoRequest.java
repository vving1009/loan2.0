package com.jiaye.cashloan.http.data.taobao;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildRequest;

/**
 * UpdateTaoBaoRequest
 *
 * @author 贾博瑄
 */
public class UpdateTaoBaoRequest extends SatcatcheChildRequest {

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
        return "CL032";
    }
}
