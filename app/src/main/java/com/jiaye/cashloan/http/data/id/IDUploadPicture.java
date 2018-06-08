package com.jiaye.cashloan.http.data.id;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildResponse;

/**
 * IDUploadPicture
 *
 * @author 贾博瑄
 */

public class IDUploadPicture extends SatcatcheChildResponse {

    @SerializedName("pic_id")
    private String picId;

    public String getPicId() {
        return picId;
    }

    public void setPicId(String picId) {
        this.picId = picId;
    }
}
