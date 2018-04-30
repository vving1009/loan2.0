package com.jiaye.cashloan.http.data.auth;

import com.jiaye.cashloan.http.base.ChildRequest;

/**
 * AuthRequest
 *
 * @author 贾博瑄
 */

public class AuthRequest extends ChildRequest {

    @Override
    protected String getBusiness() {
        return "CL007";
    }
}
