package com.jiaye.cashloan.http.data.loan;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.ChildRequest;

/**
 * WatchContractRequest
 *
 * @author 贾博瑄
 */

public class WatchContractRequest extends ChildRequest {

    @SerializedName("el_id")
    private String contractId;

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    @Override
    protected String getBusiness() {
        return "CL058";
    }
}
