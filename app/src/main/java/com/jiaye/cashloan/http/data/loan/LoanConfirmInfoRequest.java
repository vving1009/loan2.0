package com.jiaye.cashloan.http.data.loan;

import com.jiaye.cashloan.http.base.SatcatcheChildRequest;

/**
 * LoanConfirmInfoRequest
 *
 * @author 贾博瑄
 */

public class LoanConfirmInfoRequest extends SatcatcheChildRequest {

    @Override
    protected String getBusiness() {
        return "CL024";
    }
}
