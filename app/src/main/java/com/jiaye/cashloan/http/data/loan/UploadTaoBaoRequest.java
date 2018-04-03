package com.jiaye.cashloan.http.data.loan;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.utils.UploadLoanId;

/**
 * UploadTaoBaoRequest
 *
 * @author 贾博瑄
 */
public class UploadTaoBaoRequest extends SaveTaoBaoRequest {

    @SerializedName("jla_id")
    private String loanId = UploadLoanId.queryLoanId();

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }
}
