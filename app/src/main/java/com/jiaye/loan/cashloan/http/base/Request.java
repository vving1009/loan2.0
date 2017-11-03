package com.jiaye.loan.cashloan.http.base;

import com.google.gson.annotations.SerializedName;

/**
 * Request
 *
 * @author 贾博瑄
 */

public class Request<T> {

    @SerializedName("jycl_content")
    private RequestContent<T> content;

    public RequestContent<T> getContent() {
        return content;
    }

    public void setContent(RequestContent<T> content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Request{" +
                "content=" + content +
                '}';
    }
}
