package com.jiaye.cashloan.http.tongdun;

import com.google.gson.annotations.SerializedName;

/**
 * TongDunFaceRequest
 *
 * @author 贾博瑄
 */

public class TongDunFaceRequest {

    @SerializedName("name")
    private String name;

    @SerializedName("id_number")
    private String id;

    @SerializedName("image")
    private String base64;

    /*0代表照片的base64编码进行比对,1代表通过活体SDK捕获照片加密包进行比对*/
    @SerializedName("type")
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
