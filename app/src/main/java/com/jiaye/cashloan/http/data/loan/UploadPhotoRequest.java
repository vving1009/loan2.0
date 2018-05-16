package com.jiaye.cashloan.http.data.loan;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildRequest;

/**
 * UploadPhotoRequest
 *
 * @author 贾博瑄
 */
public class UploadPhotoRequest extends SatcatcheChildRequest {

    @SerializedName("jla_id")
    private String loanId;

    @SerializedName("pic_base64")
    private String base64;

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    @Override
    protected String getBusiness() {
        return "CL075";
    }
}
