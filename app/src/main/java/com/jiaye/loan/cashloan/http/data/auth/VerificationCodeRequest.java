package com.jiaye.loan.cashloan.http.data.auth;

import com.google.gson.annotations.SerializedName;
import com.jiaye.loan.cashloan.http.base.ChildRequest;

/**
 * VerificationCodeRequest
 *
 * @author 贾博瑄
 */

public abstract class VerificationCodeRequest extends ChildRequest {

    /*类型*/
    @SerializedName("msg_status")
    private String status = getStatus();

    protected abstract String getStatus();
}
