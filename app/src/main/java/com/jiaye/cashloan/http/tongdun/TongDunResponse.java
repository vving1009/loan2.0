package com.jiaye.cashloan.http.tongdun;

import com.google.gson.annotations.SerializedName;

/**
 * TongDunResponse
 *
 * @author 贾博瑄
 */

public class TongDunResponse<T> {

    @SerializedName("success")
    private boolean success;

    @SerializedName("reason_code")
    private String reasonCode;

    @SerializedName("reason_desc")
    private String reasonDesc;

    @SerializedName("result")
    private int result;

    @SerializedName("message")
    private T body;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getReasonDesc() {
        return reasonDesc;
    }

    public void setReasonDesc(String reasonDesc) {
        this.reasonDesc = reasonDesc;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
