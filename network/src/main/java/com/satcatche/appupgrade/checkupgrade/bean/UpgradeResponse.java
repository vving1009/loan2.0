package com.satcatche.appupgrade.checkupgrade.bean;

import com.google.gson.annotations.SerializedName;

/**
 * UpgradeResponse
 *
 * @author 贾博瑄
 */
public class UpgradeResponse {

    @SerializedName("return_code")
    private String returnCode;

    @SerializedName("return_msg")
    private String returnMsg;

    @SerializedName("deup_resData")
    private Body body;

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public static class Body {

        @SerializedName("version_name")
        private String versionName;

        @SerializedName("version_code")
        private int versionCode;

        @SerializedName("download_url")
        private String downloadUrl;

        @SerializedName("notes")
        private String notes;

        @SerializedName("isforce")
        private int isForce;

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(int versionCode) {
            this.versionCode = versionCode;
        }

        public String getDownloadUrl() {
            return downloadUrl;
        }

        public void setDownloadUrl(String downloadUrl) {
            this.downloadUrl = downloadUrl;
        }

        public String getNotes() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes = notes;
        }

        public int getIsForce() {
            return isForce;
        }

        public void setIsForce(int isForce) {
            this.isForce = isForce;
        }
    }
}
