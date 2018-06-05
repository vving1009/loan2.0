package com.jiaye.cashloan.http.data.certification;

import com.google.gson.annotations.SerializedName;

/**
 * Step
 *
 * @author 贾博瑄
 */
public class Step {

    @SerializedName("step")
    private int step;

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }
}
