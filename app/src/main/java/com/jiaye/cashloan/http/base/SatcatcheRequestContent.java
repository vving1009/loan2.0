package com.jiaye.cashloan.http.base;

import com.google.gson.annotations.SerializedName;

/**
 * SatcatcheRequestContent
 *
 * @author 贾博瑄
 */
public class SatcatcheRequestContent<T extends SatcatcheChildRequest> {

    @SerializedName("jycl_pubData")
    private SatcatcheRequestHeader header;

    @SerializedName("jycl_reqData")
    private T body;

    public SatcatcheRequestHeader getHeader() {
        return header;
    }

    public void setHeader(SatcatcheRequestHeader header) {
        this.header = header;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "SatcatcheRequestContent{" +
                "header=" + header +
                ", body=" + body +
                '}';
    }
}
