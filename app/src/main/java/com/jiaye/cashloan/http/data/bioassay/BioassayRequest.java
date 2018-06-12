package com.jiaye.cashloan.http.data.bioassay;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildRequest;

/**
 * BioassayRequest
 *
 * @author 贾博瑄
 */

public class BioassayRequest extends SatcatcheChildRequest {

    @SerializedName("jla_id")
    private String loanId;

    @SerializedName("pic_id")
    private String picId;

    /*0 未通过 1 通过*/
    @SerializedName("isPass")
    private int isPass;

    @SerializedName("similarity")
    private float similarity;

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

    public int getIsPass() {
        return isPass;
    }

    public void setIsPass(int isPass) {
        this.isPass = isPass;
    }

    public float getSimilarity() {
        return similarity;
    }

    public void setSimilarity(float similarity) {
        this.similarity = similarity;
    }

    @Override
    protected String getBusiness() {
        return "CL012";
    }
}
