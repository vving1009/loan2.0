package com.jiaye.cashloan.http.data.loan;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.ChildRequest;

/**
 * UploadFileRequest
 *
 * @author 贾博瑄
 */
public class UploadFileRequest extends ChildRequest {

    @SerializedName("jla_id")
    private String loanId;

    @SerializedName("pic_type")
    private int type;

    @SerializedName("pic_base64")
    private String base64;

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    @Override
    protected String getBusiness() {
        return "CL074";
    }
}
