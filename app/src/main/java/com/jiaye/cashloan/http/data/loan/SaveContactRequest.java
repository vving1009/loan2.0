package com.jiaye.cashloan.http.data.loan;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildRequest;

/**
 * SaveContactRequest
 *
 * @author 贾博瑄
 */

public class SaveContactRequest extends SatcatcheChildRequest {

    @SerializedName("jla_id")
    private String loanId;

    @SerializedName("link_data")
    private ContactData[] data;

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public ContactData[] getData() {
        return data;
    }

    public void setData(ContactData[] data) {
        this.data = data;
    }

    @Override
    protected String getBusiness() {
        return "CL014";
    }
}
