package com.jiaye.cashloan.http.tongdun;

import com.google.gson.annotations.SerializedName;

/**
 * TongDunOCR
 *
 * @author 贾博瑄
 */

public abstract class TongDunOCR {

    @SerializedName("result_message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
