package com.jiaye.cashloan.http.data.car;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildResponse;

public class QueryValuationResponse extends SatcatcheChildResponse {

    /**
     * valuation : 23
     * 单位：万元
     */

    @SerializedName("valuation")
    private String valuation;

    public String getValuation() {
        return valuation;
    }

    public void setValuation(String valuation) {
        this.valuation = valuation;
    }
}
