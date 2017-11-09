package com.jiaye.cashloan.http.gongxinbao;

import com.google.gson.annotations.SerializedName;

/**
 * GongXinBaoResponse
 *
 * @author 贾博瑄
 */

public class GongXinBaoResponse<T> {

    @SerializedName("retCode")
    private int code;

    @SerializedName("retMsg")
    private String message;

    @SerializedName("data")
    private T body;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
