package com.jiaye.cashloan.http.data.auth;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.utils.UploadLoanId;

/**
 * UploadSesameRequest
 *
 * @author 贾博瑄
 */
public class UploadSesameRequest {

    @SerializedName("jla_id")
    private String loanId = UploadLoanId.queryLoanId();

    @SerializedName("sesame_credit")
    private String source;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }
}
