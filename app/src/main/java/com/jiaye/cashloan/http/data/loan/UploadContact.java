package com.jiaye.cashloan.http.data.loan;

import com.google.gson.annotations.SerializedName;

/**
 * UploadContact
 *
 * @author 贾博瑄
 */

public class UploadContact {

    @SerializedName("return_code")
    private String code;

    @SerializedName("return_msg")
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
