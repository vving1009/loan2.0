package com.jiaye.cashloan.http.base;

import com.google.gson.annotations.SerializedName;

/**
 * SatcatcheResponseContent
 *
 * @author 贾博瑄
 */
public class SatcatcheResponseContent<T extends SatcatcheChildResponse> {

    @SerializedName("return_code")
    private String code;

    @SerializedName("return_msg")
    private String msg;

    @SerializedName("jycl_resData")
    private T body;

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

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
