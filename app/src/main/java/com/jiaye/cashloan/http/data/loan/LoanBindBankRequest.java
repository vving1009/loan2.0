package com.jiaye.cashloan.http.data.loan;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.ChildRequest;

/**
 * LoanBindBankRequest
 *
 * @author 贾博瑄
 */

public class LoanBindBankRequest extends ChildRequest {

    @SerializedName("obligate_phone")
    private String phone;

    @SerializedName("accoutn_bank")
    private String bank;

    @SerializedName("card_no")
    private String number;

    @SerializedName("source")
    private String source = "02";

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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    protected String getBusiness() {
        return "CL027";
    }
}
