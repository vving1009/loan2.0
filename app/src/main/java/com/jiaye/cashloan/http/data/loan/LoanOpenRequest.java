package com.jiaye.cashloan.http.data.loan;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.ChildRequest;

/**
 * LoanOpenRequest
 *
 * @author 贾博瑄
 */

public class LoanOpenRequest extends ChildRequest {

    @SerializedName("mobile")
    private String phone;

    @SerializedName("id_no")
    private String id;

    @SerializedName("u_name")
    private String name;

    @SerializedName("card_no")
    private String card;

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

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
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
        return "CL049";
    }
}
