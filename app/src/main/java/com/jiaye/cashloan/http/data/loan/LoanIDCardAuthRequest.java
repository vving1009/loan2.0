package com.jiaye.cashloan.http.data.loan;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.ChildRequest;

/**
 * LoanIDCardAuthRequest
 *
 * @author 贾博瑄
 */

public class LoanIDCardAuthRequest extends ChildRequest {

    @SerializedName("jla_id")
    private String loanId;

    @SerializedName("id_number")
    private String id;

    @SerializedName("jcb_name")
    private String name;

    @SerializedName("birthday")
    private String birthday;

    @SerializedName("gender")
    private String gender;

    @SerializedName("nation")
    private String nation;

    @SerializedName("address")
    private String address;

    @SerializedName("valid_date_begin")
    private String dateBeigin;

    @SerializedName("valid_date_end")
    private String dateEnd;

    @SerializedName("agency")
    private String agency;

    @SerializedName("pic_zid")
    private String picFrontId;

    @SerializedName("pic_fid")
    private String picBackId;

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDateBeigin() {
        return dateBeigin;
    }

    public void setDateBeigin(String dateBeigin) {
        this.dateBeigin = dateBeigin;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getPicFrontId() {
        return picFrontId;
    }

    public void setPicFrontId(String picFrontId) {
        this.picFrontId = picFrontId;
    }

    public String getPicBackId() {
        return picBackId;
    }

    public void setPicBackId(String picBackId) {
        this.picBackId = picBackId;
    }

    @Override
    protected String getBusiness() {
        return "CL020";
    }
}
