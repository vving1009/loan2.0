package com.jiaye.cashloan.http.data.loan;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.ChildResponse;

/**
 * CheckLoan
 *
 * @author 贾博瑄
 */

public class CheckLoan extends ChildResponse {

    @SerializedName("if_lend")
    private String check;

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }
}
