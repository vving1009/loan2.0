package com.jiaye.cashloan.http.data.loan;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildResponse;

/**
 * LoanInfoAuth
 *
 * @author 贾博瑄
 */

public class LoanInfoAuth extends SatcatcheChildResponse {

    /**
     * 个人基本信息提交状态（0：未提交，1：已提交）
     */
    @SerializedName("user_baseinfo_msg")
    private int person;

    /**
     * 联系人信息提交状态（0：未提交，1：已提交）
     */
    @SerializedName("user_linkman_msg")
    private int contact;

    public int getPerson() {
        return person;
    }

    public void setPerson(int person) {
        this.person = person;
    }

    public int getContact() {
        return contact;
    }

    public void setContact(int contact) {
        this.contact = contact;
    }
}
