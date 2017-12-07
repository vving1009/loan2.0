package com.jiaye.cashloan.http.data.my;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.ChildResponse;

/**
 * Bank
 *
 * @author 贾博瑄
 */

public class Bank extends ChildResponse {

    @SerializedName("jcb_name")
    private String name;

    @SerializedName("obligate_phone")
    private String phone;

    @SerializedName("account_bank")
    private String bank;

    @SerializedName("card_no")
    private String number;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
