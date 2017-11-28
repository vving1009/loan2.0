package com.jiaye.cashloan.http.data.loan;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.ChildRequest;

/**
 * LoanFaceAuthRequest
 *
 * @author 贾博瑄
 */

public class LoanFaceAuthRequest extends ChildRequest {

    @SerializedName("pic_id")
    private String picId;

    @SerializedName("isPass")
    private boolean isPass;

    @Override
    protected String getBusiness() {
        return "CL021";
    }

    public String getPicId() {
        return picId;
    }

    public void setPicId(String picId) {
        this.picId = picId;
    }

    public boolean isPass() {
        return isPass;
    }

    public void setPass(boolean pass) {
        isPass = pass;
    }
}
