package com.jiaye.cashloan.http.base;

import com.google.gson.annotations.SerializedName;

/**
 * SatcatcheRequest
 *
 * @author 贾博瑄
 */
public class SatcatcheRequest<T extends SatcatcheChildRequest> {

    @SerializedName("jycl_content")
    private SatcatcheRequestContent<T> content;

    public SatcatcheRequestContent<T> getContent() {
        return content;
    }

    public void setContent(SatcatcheRequestContent<T> content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Request{" +
                "content=" + content +
                '}';
    }
}
