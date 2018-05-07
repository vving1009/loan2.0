package com.jiaye.cashloan.http.data.loan;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildRequest;

/**
 * LoanBindBankRequest
 *
 * @author 贾博瑄
 */

public class LoanBindBankRequest extends SatcatcheChildRequest {

    @SerializedName("obligate_phone")
    private String phone;

    @SerializedName("accoutn_bank")
    private String bank;

    @SerializedName("card_no")
    private String number;

    @SerializedName("source")
    private String source;

    @SerializedName("sms_code")
    private String sms;

    @SerializedName("lastserviceauth_code")
    private String code;

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

    public String getSms() {
        return sms;
    }

    public void setSms(String sms) {
        this.sms = sms;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    protected String getBusiness() {
        return "CL016";
    }
}
