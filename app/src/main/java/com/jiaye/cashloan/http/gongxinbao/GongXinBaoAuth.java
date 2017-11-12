package com.jiaye.cashloan.http.gongxinbao;

import com.google.gson.annotations.SerializedName;

/**
 * GongXinBaoToken
 *
 * @author 贾博瑄
 */

public class GongXinBaoAuth {

    @SerializedName("token")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
