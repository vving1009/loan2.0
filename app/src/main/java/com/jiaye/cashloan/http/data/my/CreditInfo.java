package com.jiaye.cashloan.http.data.my;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.ChildResponse;

/**
 * CreditInfo
 *
 * @author 贾博瑄
 */

public class CreditInfo extends ChildResponse {

    @SerializedName("account_id")
    private String accountId;

    @SerializedName("id_card")
    private String id;

    @SerializedName("mobile")
    private String phone;

    @SerializedName("real_name")
    private String name;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
