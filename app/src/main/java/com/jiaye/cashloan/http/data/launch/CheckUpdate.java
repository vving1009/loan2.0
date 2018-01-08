package com.jiaye.cashloan.http.data.launch;

import com.google.gson.annotations.SerializedName;

/**
 * CheckUpdate
 *
 * @author 贾博瑄
 */

public class CheckUpdate {

    @SerializedName("code")
    private int code;

    @SerializedName("data")
    private Data data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {

        @SerializedName("versionName")
        private String versionName;

        @SerializedName("versionCode")
        private int versionCode;

        @SerializedName("downloadUrl")
        private String downloadUrl;

        @SerializedName("notes")
        private String notes;

        @SerializedName("isForce")
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
