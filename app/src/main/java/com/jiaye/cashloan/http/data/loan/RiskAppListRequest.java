package com.jiaye.cashloan.http.data.loan;

import com.jiaye.cashloan.http.base.SatcatcheChildRequest;

/**
 * RiskAppListRequest
 *
 * @author 贾博瑄
 */
public class RiskAppListRequest extends SatcatcheChildRequest {

    @Override
    protected String getBusiness() {
        return "CL090";
    }
}
