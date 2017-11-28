package com.jiaye.cashloan.http.data.loan;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.ChildRequest;

/**
 * LoanIDCardAuthRequest
 *
 * @author 贾博瑄
 */

public class LoanIDCardAuthRequest extends ChildRequest {

    @SerializedName("jcb_name")
    private String name;

    @SerializedName("id_number")
    private String id;

    @SerializedName("da_valid_period")
    private String validDate;

    @SerializedName("pic_zid")
    private String picFrontId;

    @SerializedName("pic_fid")
    private String picBackId;

    @SerializedName("da_zmresult")
    private String dataFront;

    @SerializedName("da_fmresult")
    private String dataBack;

    @Override
    protected String getBusiness() {
        return "CL020";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValidDate() {
        return validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate;
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

    public String getDataFront() {
        return dataFront;
    }

    public void setDataFront(String dataFront) {
        this.dataFront = dataFront;
    }

    public String getDataBack() {
        return dataBack;
    }

    public void setDataBack(String dataBack) {
        this.dataBack = dataBack;
    }
}
