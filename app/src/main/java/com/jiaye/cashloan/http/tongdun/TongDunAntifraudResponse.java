package com.jiaye.cashloan.http.tongdun;

import com.google.gson.annotations.SerializedName;

/**
 * TongDunAntifraudResponse
 *
 * @author 贾博瑄
 */

public class TongDunAntifraudResponse<T> {

    @SerializedName("success")
    private boolean success;

    @SerializedName("id")
    private String id;

    @SerializedName("reason_code")
    private String reasonCode;

    @SerializedName("reason_desc")
    private String reasonDesc;

    @SerializedName("result_desc")
    private T body;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
