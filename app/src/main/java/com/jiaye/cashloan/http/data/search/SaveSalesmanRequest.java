package com.jiaye.cashloan.http.data.search;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildRequest;

/**
 * SaveSalesmanRequest
 *
 * @author 贾博瑄
 */
public class SaveSalesmanRequest extends SatcatcheChildRequest {

    @SerializedName("jla_id")
    private String loanId;

    @SerializedName("office_id")
    private String companyId;

    @SerializedName("office_name")
    private String companyName;

    @SerializedName("staff_name")
    private String name;

    @SerializedName("staff_number")
    private String number;

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    @Override
    protected String getBusiness() {
        return "CL004";
    }
}
