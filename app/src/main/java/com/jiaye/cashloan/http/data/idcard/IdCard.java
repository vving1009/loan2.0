package com.jiaye.cashloan.http.data.idcard;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildResponse;

public class IdCard extends SatcatcheChildResponse {


    /**
     * id_card : 120101198610240518
     * id_name : xxx
     */

    @SerializedName("id_card")
    private String id;
    @SerializedName("id_name")
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
