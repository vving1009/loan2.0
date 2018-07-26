package com.satcatche.appupgrade.checkupgrade.bean;

import com.google.gson.annotations.SerializedName;

/**
 * SatcatcheRequest
 *
 * @author 贾博瑄
 */
public class UpgradeRequest {

    @SerializedName("deup_reqData")
    private Body body;

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public static class Body {

        @SerializedName("version_code")
        private int versionCode;

        @SerializedName("app_package")
        private String appPackage;

        @SerializedName("os")
        private String os = "android";

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(int versionCode) {
            this.versionCode = versionCode;
        }

        public String getAppPackage() {
            return appPackage;
        }

        public void setAppPackage(String appPackage) {
            this.appPackage = appPackage;
        }

        public String getOs() {
            return os;
        }

        public void setOs(String os) {
            this.os = os;
        }
    }
}
