package com.jiaye.cashloan.http.data.person;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.ChildResponse;

/**
 * Person
 *
 * @author 贾博瑄
 */

public class Person extends ChildResponse {

    /*最高学历*/
    @SerializedName("deu_typeid")
    private String education;

    /*婚姻状况*/
    @SerializedName("marry_typeid")
    private String marriage;

    /*户籍所在城市*/
    @SerializedName("reg_city")
    private String registerCity;

    /*现居住城市*/
    @SerializedName("reside_city")
    private String city;

    /*现居住地址*/
    @SerializedName("reside_addr")
    private String address;

    /*电子邮箱*/
    @SerializedName("email")
    private String email;

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getMarriage() {
        return marriage;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }

    public String getRegisterCity() {
        return registerCity;
    }

    public void setRegisterCity(String registerCity) {
        this.registerCity = registerCity;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
