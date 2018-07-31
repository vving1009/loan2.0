package com.jiaye.cashloan.http.data.bioassay;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildRequest;

/**
 * BioassayUploadPictureRequest
 *
 * @author 贾博瑄
 */

public class BioassayUploadPictureRequest extends SatcatcheChildRequest {

    @SerializedName("jla_id")
    private String loanId;

    @SerializedName("pic_type")
    private String picType;

    @SerializedName("pic_name")
    private String picName;

    @SerializedName("pic_url")
    private String picUrl;

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getPicType() {
        return picType;
    }

    public void setPicType(String picType) {
        this.picType = picType;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    @Override
    protected String getBusiness() {
        return "CL034";
    }
}
