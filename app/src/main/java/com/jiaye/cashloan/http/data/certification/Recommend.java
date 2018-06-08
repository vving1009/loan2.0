package com.jiaye.cashloan.http.data.certification;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildResponse;

/**
 * Recommend
 *
 * @author 贾博瑄
 */
public class Recommend extends SatcatcheChildResponse {

    @SerializedName("office_id")
    private String companyId;

    @SerializedName("office_name")
    private String company;

    @SerializedName("staff_name")
    private String name;

    @SerializedName("staff_number")
    private String number;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
