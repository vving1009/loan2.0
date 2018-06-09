package com.jiaye.cashloan.http.data.login;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildResponse;

/**
 * Login
 *
 * @author 贾博瑄
 */

public class Login extends SatcatcheChildResponse {

    @SerializedName("idCard")
    private String id;

    @SerializedName("jcbName")
    private String name;

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
}
