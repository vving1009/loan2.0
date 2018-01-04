package com.jiaye.cashloan.http.data.loan;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.ChildResponse;

/**
 * LoanOpenSMS
 *
 * @author 贾博瑄
 */

public class LoanOpenSMS extends ChildResponse {

    @SerializedName("service_authCode")
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
