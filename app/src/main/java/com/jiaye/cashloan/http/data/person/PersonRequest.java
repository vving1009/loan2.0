package com.jiaye.cashloan.http.data.person;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.ChildRequest;

/**
 * PersonRequest
 *
 * @author 贾博瑄
 */

public class PersonRequest extends ChildRequest {

    @SerializedName("tel_phone")
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    protected String getBusiness() {
        return "CL008";
    }
}
