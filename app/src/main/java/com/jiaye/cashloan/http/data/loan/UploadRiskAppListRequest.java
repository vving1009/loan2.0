package com.jiaye.cashloan.http.data.loan;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildRequest;

import java.util.ArrayList;

/**
 * UploadRiskAppListRequest
 *
 * @author 贾博瑄
 */
public class UploadRiskAppListRequest extends SatcatcheChildRequest {

    @SerializedName("jla_id")
    private String loanId;

    @SerializedName("app_list")
    private ArrayList<RiskApp> list;

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public ArrayList<RiskApp> getList() {
        return list;
    }

    public void setList(ArrayList<RiskApp> list) {
        this.list = list;
    }

    public static class RiskApp {

        @SerializedName("app_name")
        private String appName;

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }
    }

    @Override
    protected String getBusiness() {
        return "CL077";
    }
}
