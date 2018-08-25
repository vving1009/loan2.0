package com.jiaye.cashloan.http.data.jdcar;

import com.google.gson.annotations.SerializedName;

public class JdCarBrand {

    @SerializedName("brandname")
    private String brandname;
    @SerializedName("id")
    private String id;
    @SerializedName("letter")
    private String letter;
    @SerializedName("picurl")
    private String picurl;

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }
}
