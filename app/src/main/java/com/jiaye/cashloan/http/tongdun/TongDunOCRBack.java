package com.jiaye.cashloan.http.tongdun;

import com.google.gson.annotations.SerializedName;

/**
 * TongDunOCRBack
 *
 * @author 贾博瑄
 */

public class TongDunOCRBack extends TongDunOCR {

    /*身份证照片类型 1 身份证正面 2 身份证背面*/
    @SerializedName("idcard_type")
    private int idCardType;

    /*身份证有效期 开始时间*/
    @SerializedName("valid_date_begin")
    private String dateBegin;

    /*身份证有效期 结束时间*/
    @SerializedName("valid_date_end")
    private String dateEnd;

    /*签发机关*/
    @SerializedName("agency")
    private String agency;

    public int getIdCardType() {
        return idCardType;
    }

    public void setIdCardType(int idCardType) {
        this.idCardType = idCardType;
    }

    public String getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(String dateBegin) {
        this.dateBegin = dateBegin;
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
}
