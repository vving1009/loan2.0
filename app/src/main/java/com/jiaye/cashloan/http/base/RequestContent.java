package com.jiaye.cashloan.http.base;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * RequestContent
 *
 * @author 贾博瑄
 */

public class RequestContent<T> {

    @SerializedName("jycl_pubData")
    private RequestHeader header;

    @SerializedName("jycl_reqData")
    private List<T> body;

    public RequestHeader getHeader() {
        return header;
    }

    public void setHeader(RequestHeader header) {
        this.header = header;
    }

    public List<T> getBody() {
        return body;
    }

    public void setBody(List<T> body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "RequestContent{" +
                "header=" + header +
                ", body=" + body +
                '}';
    }
}
