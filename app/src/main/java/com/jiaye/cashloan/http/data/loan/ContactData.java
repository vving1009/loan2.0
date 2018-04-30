package com.jiaye.cashloan.http.data.loan;

import com.google.gson.annotations.SerializedName;

/**
 * ContactData
 *
 * @author 贾博瑄
 */

public class ContactData {

    @SerializedName("link_name")
    public String name;

    @SerializedName("phone")
    public String phone;

    @SerializedName("relation_type")
    public String type;

    @SerializedName("relation")
    public String relation;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }
}
