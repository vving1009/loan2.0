package com.jiaye.cashloan.http.tongdun;

import com.google.gson.annotations.SerializedName;

/**
 * TongDunFace
 *
 * @author 贾博瑄
 */

public class TongDunFace extends TongDunOCR {

    /*相似度超过75为true*/
    @SerializedName("is_pass")
    private boolean isPass;

    /*相似度*/
    @SerializedName("similarity")
    private float similarity;

    /*type为1时解密后的图片base64编码*/
    @SerializedName("image")
    private String base64;

    public boolean isPass() {
        return isPass;
    }

    public void setPass(boolean pass) {
        isPass = pass;
    }

    public float getSimilarity() {
        return similarity;
    }

    public void setSimilarity(float similarity) {
        this.similarity = similarity;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }
}
