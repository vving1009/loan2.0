package com.jiaye.cashloan.http.data.my;

import com.jiaye.cashloan.http.base.SatcatcheChildRequest;

/**
 * IDCardAuthRequest
 *
 * @author 贾博瑄
 */

public class IDCardAuthRequest extends SatcatcheChildRequest {

    @Override
    protected String getBusiness() {
        return "CL041";
    }
}
