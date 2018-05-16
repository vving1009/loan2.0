package com.jiaye.cashloan.http.data.loan;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildResponse;

/**
 * UploadPhoto
 *
 * @author 贾博瑄
 */
public class UploadPhoto extends SatcatcheChildResponse {

    @SerializedName("jla_id")
    private String loanId;

    @SerializedName("pic_id")
    private String picId;

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getPicId() {
        return picId;
    }

    public void setPicId(String picId) {
        this.picId = picId;
    }
}
