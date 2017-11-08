package com.jiaye.cashloan.http.tongdun;

import com.google.gson.annotations.SerializedName;

/**
 * TongDunOCRFront
 *
 * @author 贾博瑄
 */

public class TongDunOCRFront {

    /*身份证照片类型 1 身份证正面 2 身份证背面*/
    @SerializedName("idcard_type")
    private int idCardType;

    /*身份证*/
    @SerializedName("id_number")
    private String idNumber;

    /*姓名*/
    @SerializedName("name")
    private String name;

    /*出生日期*/
    @SerializedName("birthday")
    private String birthday;

    /*性别*/
    @SerializedName("gender")
    private String gender;

    /*民族*/
    @SerializedName("nation")
    private String nation;

    /*地址信息*/
    @SerializedName("address")
    private String address;

    public int getIdCardType() {
        return idCardType;
    }

    public void setIdCardType(int idCardType) {
        this.idCardType = idCardType;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
