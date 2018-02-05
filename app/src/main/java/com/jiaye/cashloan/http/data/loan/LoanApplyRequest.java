package com.jiaye.cashloan.http.data.loan;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.ChildRequest;

/**
 * LoanApplyRequest
 *
 * @author 贾博瑄
 */

public class LoanApplyRequest extends ChildRequest {

    @SerializedName("jk_status")
    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    protected String getBusiness() {
        return "CL060";
    }
}
