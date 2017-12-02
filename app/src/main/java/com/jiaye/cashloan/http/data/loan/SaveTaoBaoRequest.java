package com.jiaye.cashloan.http.data.loan;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.ChildRequest;

/**
 * SaveTaoBaoRequest
 *
 * @author 贾博瑄
 */

public class SaveTaoBaoRequest extends ChildRequest {

    @SerializedName("req_token")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    protected String getBusiness() {
        return "CL019";
    }
}
