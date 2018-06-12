package com.jiaye.cashloan.http.data.vehcile;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildRequest;

import java.util.List;

/**
 * UploadFaceRequest
 *
 * @author 贾博瑄
 */
public class UploadFaceRequest extends SatcatcheChildRequest {

    @SerializedName("jla_id")
    private String loanId;

    @SerializedName("pic_urls")
    private List<Data> list;

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public List<Data> getList() {
        return list;
    }

    public void setList(List<Data> list) {
        this.list = list;
    }

    @Override
    protected String getBusiness() {
        return "CL023";
    }

    public static class Data {

        @SerializedName("pic_url")
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
