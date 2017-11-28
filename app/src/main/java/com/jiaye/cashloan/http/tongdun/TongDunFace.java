package com.jiaye.cashloan.http.tongdun;

import com.google.gson.annotations.SerializedName;

/**
 * TongDunFace
 *
 * @author 贾博瑄
 */

public class TongDunFace {

    /*相似度超过75为true*/
    @SerializedName("is_pass")
    private boolean isPass;

    /*相似度*/
    @SerializedName("similarity")
    private float similarity;

    /*type为1时解密后的图片base64编码*/
    @SerializedName("image")
    private String base64;

    /*身份证网纹照base64编码*/
    @SerializedName("origin_image")
    private String originBase64;

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

    public String getOriginBase64() {
        return originBase64;
    }

    public void setOriginBase64(String originBase64) {
        this.originBase64 = originBase64;
    }
}
