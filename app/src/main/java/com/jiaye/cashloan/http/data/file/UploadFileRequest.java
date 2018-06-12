package com.jiaye.cashloan.http.data.file;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildRequest;

/**
 * UploadFileRequest
 *
 * @author 贾博瑄
 */
public class UploadFileRequest extends SatcatcheChildRequest {

    @SerializedName("jla_id")
    private String loanId;

    @SerializedName("pic_type")
    private int type;

    @SerializedName("pic_name")
    private String name;

    @SerializedName("pic_url")
    private String url;

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    protected String getBusiness() {
        return "CL019";
    }
}
