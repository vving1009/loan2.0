package com.jiaye.cashloan.http.data.loan;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.utils.UploadLoanId;

/**
 * UploadBioAssayRequest
 *
 * @author 贾博瑄
 */
public class UploadBioAssayRequest {

    @SerializedName("jla_id")
    private String loanId = UploadLoanId.queryLoanId();

    @SerializedName("pic_id")
    private String picId;

    @SerializedName("is_pass")
    private String isPass;

    @SerializedName("similarity")
    private String similarity;

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

    public String getIsPass() {
        return isPass;
    }

    public void setIsPass(String isPass) {
        this.isPass = isPass;
    }

    public String getSimilarity() {
        return similarity;
    }

    public void setSimilarity(String similarity) {
        this.similarity = similarity;
    }
}
