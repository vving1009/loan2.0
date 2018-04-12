package com.jiaye.cashloan.http.base;

import com.google.gson.annotations.SerializedName;

/**
 * SatcatcheResponse
 *
 * @author 贾博瑄
 */
public class SatcatcheResponse<T extends SatcatcheChildResponse> {

    @SerializedName("jycl_content")
    private SatcatcheResponseContent<T> content;

    public SatcatcheResponseContent<T> getContent() {
        return content;
    }

    public void setContent(SatcatcheResponseContent<T> content) {
        this.content = content;
    }
}
