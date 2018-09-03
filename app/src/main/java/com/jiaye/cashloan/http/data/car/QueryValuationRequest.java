package com.jiaye.cashloan.http.data.car;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildRequest;

public class QueryValuationRequest extends SatcatcheChildRequest {

    @SerializedName("jla_id")
    private String jlaId;

    public String getJlaId() {
        return jlaId;
    }

    public void setJlaId(String jlaId) {
        this.jlaId = jlaId;
    }

    @Override
    protected String getBusiness() {
        return "CL042";
    }
}
