package com.jiaye.cashloan.http.data.loan;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.ChildResponse;

/**
 * LoanInfoAuth
 *
 * @author 贾博瑄
 */

public class LoanInfoAuth extends ChildResponse {

    @SerializedName("user_baseinfo_msg")
    private String person;

    @SerializedName("user_linkman_msg")
    private String contact;

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
