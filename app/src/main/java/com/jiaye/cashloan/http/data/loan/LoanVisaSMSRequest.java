package com.jiaye.cashloan.http.data.loan;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.ChildRequest;

/**
 * LoanVisaSMSRequest
 *
 * @author 贾博瑄
 */

public class LoanVisaSMSRequest extends ChildRequest {

    @SerializedName("compact_type")
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    protected String getBusiness() {
        return "CL054";
    }
}
