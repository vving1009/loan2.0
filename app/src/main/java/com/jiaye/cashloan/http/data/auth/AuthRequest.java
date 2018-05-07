package com.jiaye.cashloan.http.data.auth;

import com.jiaye.cashloan.http.base.SatcatcheChildRequest;

/**
 * AuthRequest
 *
 * @author 贾博瑄
 */

public class AuthRequest extends SatcatcheChildRequest {

    @Override
    protected String getBusiness() {
        return "CL007";
    }
}
