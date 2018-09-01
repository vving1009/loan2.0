package com.jiaye.cashloan.http.data.car;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildRequest;

public class SaveValuationRequest extends SatcatcheChildRequest {

    /**
     * jla_id : 借款编号
     * valuation : 车辆估值
     * vin : 车辆识别号 可为空
     */

    @SerializedName("jla_id")
    private String jlaId;
    @SerializedName("valuation")
    private String valuation;
    @SerializedName("vin")
    private String vin = "";

    @Override
    protected String getBusiness() {
        return "CL036";
    }

    public String getJlaId() {
        return jlaId;
    }

    public void setJlaId(String jlaId) {
        this.jlaId = jlaId;
    }

    public String getValuation() {
        return valuation;
    }

    public void setValuation(String valuation) {
        this.valuation = valuation;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }
}
