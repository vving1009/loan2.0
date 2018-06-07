package com.jiaye.cashloan.http.data.certification;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildResponse;

/**
 * Step
 *
 * @author 贾博瑄
 */
public class Step extends SatcatcheChildResponse {

    @SerializedName("exmine_status")
    private int step;

    @SerializedName("exmine_msg")
    private String msg;

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
