package com.jiaye.cashloan.http.data.idcard;

import com.jiaye.cashloan.http.base.SatcatcheChildRequest;

public class IdCardRequest extends SatcatcheChildRequest {
    @Override
    protected String getBusiness() {
        return "CL039";
    }
}
