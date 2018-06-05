package com.jiaye.cashloan.http.data.certification;

import com.google.gson.annotations.SerializedName;

/**
 * Recommend
 *
 * @author 贾博瑄
 */
public class Recommend {

    @SerializedName("company")
    private String company;

    @SerializedName("number")
    private String number;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
