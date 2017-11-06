package com.jiaye.cashloan.http.base;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * ResponseContent
 *
 * @author 贾博瑄
 */

public class ResponseContent<T> {

    @SerializedName("jycl_resHead")
    private ResponseHeader header;

    @SerializedName("jycl_resData")
    private List<T> body;

    public ResponseHeader getHeader() {
        return header;
    }

    public void setHeader(ResponseHeader header) {
        this.header = header;
    }

    public List<T> getBody() {
        return body;
    }

    public void setBody(List<T> body) {
        this.body = body;
    }
}
