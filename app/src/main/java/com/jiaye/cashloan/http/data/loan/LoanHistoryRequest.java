package com.jiaye.cashloan.http.data.loan;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.ChildRequest;

/**
 * LoanHistoryRequest
 *
 * @author 贾博瑄
 */

public class LoanHistoryRequest extends ChildRequest {

    @SerializedName("tel_phone")
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    protected String getBusiness() {
        return "CL030";
    }
}
