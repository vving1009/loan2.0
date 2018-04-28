package com.jiaye.cashloan.http.data.my;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildResponse;

/**
 * IDCardAuth
 *
 * @author 贾博瑄
 */

public class IDCardAuth extends SatcatcheChildResponse {

    @SerializedName("jcb_name")
    private String name;

    @SerializedName("id_number")
    private String number;

    @SerializedName("da_valid_period")
    private String date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
