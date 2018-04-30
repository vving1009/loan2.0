package com.jiaye.cashloan.http.data.launch;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildResponse;

/**
 * CheckUpdate
 *
 * @author 贾博瑄
 */

public class CheckUpdate extends SatcatcheChildResponse {

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
