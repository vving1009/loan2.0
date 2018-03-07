package com.jiaye.cashloan.http.data.loan;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.ChildRequest;

/**
 * LoanOpenSMSRequest
 *
 * @author 贾博瑄
 */

public class LoanOpenSMSRequest extends ChildRequest {

    @SerializedName("card_no")
    private String card;

    @SerializedName("card_phone")
    private String phone;

    /* accountOpenPlus cardBindPlus */
    @SerializedName("service_tradeCode")
    private String code;

    @SerializedName("req_type")
    private String type = "1";

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    protected String getBusiness() {
        return "CL047";
    }
}
