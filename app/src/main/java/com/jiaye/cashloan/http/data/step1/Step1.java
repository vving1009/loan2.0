package com.jiaye.cashloan.http.data.step1;

import com.google.gson.annotations.SerializedName;

/**
 * Step1
 *
 * @author 贾博瑄
 */
public class Step1 {

    @SerializedName("name")
    private String name;

    @SerializedName("state")
    private int state;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
