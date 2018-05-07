package com.jiaye.cashloan.http.data.loan;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildRequest;

/**
 * SesameRequest
 *
 * @author 贾博瑄
 */

public class SesameRequest extends SatcatcheChildRequest {

    @SerializedName("name")
    private String name;

    @SerializedName("cert_no")
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    protected String getBusiness() {
        return "CL038";
    }
}
