package com.jiaye.cashloan.http.data.loan;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.tongdun.TongDunOCRBack;
import com.jiaye.cashloan.http.tongdun.TongDunOCRFront;
import com.jiaye.cashloan.http.utils.UploadLoanId;

/**
 * UploadAuthRequest
 *
 * @author 贾博瑄
 */
public class UploadAuthRequest {

    @SerializedName("jla_id")
    private String loanId = UploadLoanId.queryLoanId();

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
    private TongDunOCRFront dataFront;

    @SerializedName("da_fmresult")
    private TongDunOCRBack dataBack;

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
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

    public TongDunOCRFront getDataFront() {
        return dataFront;
    }

    public void setDataFront(TongDunOCRFront dataFront) {
        this.dataFront = dataFront;
    }

    public TongDunOCRBack getDataBack() {
        return dataBack;
    }

    public void setDataBack(TongDunOCRBack dataBack) {
        this.dataBack = dataBack;
    }
}
