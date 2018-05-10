package com.jiaye.cashloan.http.data.loan;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildResponse;

import java.util.ArrayList;

/**
 * RiskAppList
 *
 * @author 贾博瑄
 */
public class RiskAppList extends SatcatcheChildResponse {

    @SerializedName("app_list")
    private ArrayList<RiskApp> list;

    public ArrayList<RiskApp> getList() {
        return list;
    }

    public void setList(ArrayList<RiskApp> list) {
        this.list = list;
    }

    public static class RiskApp {

        @SerializedName("app_name")
        private String appName;

        @SerializedName("app_package")
        private String appPackage;

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }

        public String getAppPackage() {
            return appPackage;
        }

        public void setAppPackage(String appPackage) {
            this.appPackage = appPackage;
        }
    }

}
