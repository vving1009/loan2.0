package com.jiaye.cashloan.http.tongdun;

import com.google.gson.annotations.SerializedName;

/**
 * TongDunAntifraud
 *
 * @author 贾博瑄
 */

public class TongDunAntifraud<T> {

    @SerializedName("ANTIFRAUD_INFOQUERY")
    private T antifraud;

    public T getAntifraud() {
        return antifraud;
    }

    public void setAntifraud(T antifraud) {
        this.antifraud = antifraud;
    }
}
