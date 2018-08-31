package com.jiaye.cashloan.http.data.car;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CarResponse<T> {

    /**
     * reason : 成功返回
     * error_code : 0
     * result :
     */

    @SerializedName("reason")
    private String reason;
    @SerializedName("error_code")
    private int errorCode;
    @SerializedName("result")
    private T result;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
