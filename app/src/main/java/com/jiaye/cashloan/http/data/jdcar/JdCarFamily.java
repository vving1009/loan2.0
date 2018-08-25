package com.jiaye.cashloan.http.data.jdcar;

import com.google.gson.annotations.SerializedName;

public class JdCarFamily {

    /**
     * brandid : 16
     * brandname : 一汽
     * factoryname : 一汽吉林
     * familyname : 佳宝V70
     * id : 369
     */

    @SerializedName("brandid")
    private String brandid;
    @SerializedName("brandname")
    private String brandname;
    @SerializedName("factoryname")
    private String factoryname;
    @SerializedName("familyname")
    private String familyname;
    @SerializedName("id")
    private String id;

    public String getBrandid() {
        return brandid;
    }

    public void setBrandid(String brandid) {
        this.brandid = brandid;
    }

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    public String getFactoryname() {
        return factoryname;
    }

    public void setFactoryname(String factoryname) {
        this.factoryname = factoryname;
    }

    public String getFamilyname() {
        return familyname;
    }

    public void setFamilyname(String familyname) {
        this.familyname = familyname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
