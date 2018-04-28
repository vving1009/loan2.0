package com.jiaye.cashloan.http.data.loan;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildResponse;

/**
 * LoanUploadPicture
 *
 * @author 贾博瑄
 */

public class LoanUploadPicture extends SatcatcheChildResponse {

    @SerializedName("pic_id")
    private String picId;

    public String getPicId() {
        return picId;
    }

    public void setPicId(String picId) {
        this.picId = picId;
    }
}
