package com.jiaye.cashloan.http.data.loan;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.utils.UploadLoanId;

/**
 * UploadOperatorRequest
 *
 * @author 贾博瑄
 */
public class UploadOperatorRequest extends SavePhoneRequest {

    @SerializedName("jla_id")
    private String loanId = UploadLoanId.queryLoanId();

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }
}
