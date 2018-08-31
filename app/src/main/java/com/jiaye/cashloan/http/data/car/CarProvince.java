package com.jiaye.cashloan.http.data.car;

import com.google.gson.annotations.SerializedName;

public class CarProvince {

    /**
     * proID : 1   //省份的编码
     * proName : 北京市  //省份的名称
     * pinyin : beijing  //省份的名称拼音
     * pin : B  //省份的首字母
     * logo : null
     * hits : 0
     */

    @SerializedName("proID")
    private String proID;
    @SerializedName("proName")
    private String proName;
    @SerializedName("pinyin")
    private String pinyin;
    @SerializedName("pin")
    private String pin;
    @SerializedName("logo")
    private Object logo;
    @SerializedName("hits")
    private String hits;

    public String getProID() {
        return proID;
    }

    public void setProID(String proID) {
        this.proID = proID;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Object getLogo() {
        return logo;
    }

    public void setLogo(Object logo) {
        this.logo = logo;
    }

    public String getHits() {
        return hits;
    }

    public void setHits(String hits) {
        this.hits = hits;
    }
}
