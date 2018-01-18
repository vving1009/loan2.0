package com.jiaye.cashloan.http.data.loan;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.ChildRequest;

/**
 * LoanVisaSignRequest
 *
 * @author 贾博瑄
 */

public class LoanVisaSignRequest extends ChildRequest {

    @SerializedName("tel_code")
    private String sms;

    @SerializedName("compact_type")
    private String type;

    public String getSms() {
        return sms;
    }

    public void setSms(String sms) {
        this.sms = sms;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    protected String getBusiness() {
        return "CL055";
    }
}
