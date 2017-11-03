package com.jiaye.loan.cashloan.http.base;

import com.google.gson.annotations.SerializedName;

/**
 * Response
 *
 * @author 贾博瑄
 */

public class Response<T> {

    @SerializedName("jycl_content")
    private ResponseContent<T> content;

    public ResponseContent<T> getContent() {
        return content;
    }

    public void setContent(ResponseContent<T> content) {
        this.content = content;
    }
}
