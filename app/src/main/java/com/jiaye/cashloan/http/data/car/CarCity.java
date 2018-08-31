package com.jiaye.cashloan.http.data.car;

import com.google.gson.annotations.SerializedName;

public class CarCity {

    /**
     * cityID : 98 //城市id
     * cityName : 宿迁市 //城市名称
     * proID : 11  //省份id
     * value : 0.986801
     * area : 2
     * pinyin : suqian  //城市名称拼音
     * enter : 国4排放标准（除低速车、挂车、摩托车和质量大于3.5吨车辆）；不符合标准的，需买方写保证  //准入标准
     * hits : 25
     */

    @SerializedName("cityID")
    private String cityID;
    @SerializedName("cityName")
    private String cityName;
    @SerializedName("proID")
    private String proID;
    @SerializedName("value")
    private String value;
    @SerializedName("area")
    private String area;
    @SerializedName("pinyin")
    private String pinyin;
    @SerializedName("enter")
    private String enter;
    @SerializedName("hits")
    private String hits;

    public String getCityID() {
        return cityID;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getProID() {
        return proID;
    }

    public void setProID(String proID) {
        this.proID = proID;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getEnter() {
        return enter;
    }

    public void setEnter(String enter) {
        this.enter = enter;
    }

    public String getHits() {
        return hits;
    }

    public void setHits(String hits) {
        this.hits = hits;
    }
}
