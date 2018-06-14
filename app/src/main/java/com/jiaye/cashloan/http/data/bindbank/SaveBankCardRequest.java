package com.jiaye.cashloan.http.data.bindbank;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildRequest;

/**
 * SaveBankCardRequest
 *
 * @author 贾博瑄
 */
public class SaveBankCardRequest extends SatcatcheChildRequest {

    @SerializedName("bank_name")
    private String name;

    @SerializedName("bank_card_num")
    private String number;

    @SerializedName("reserved_phone")
    private String phone;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    protected String getBusiness() {
        return "CL024";
    }
}
