package com.jiaye.cashloan.http.data.loan;

import com.jiaye.cashloan.http.base.SatcatcheChildRequest;

public class LoanDateRequest extends SatcatcheChildRequest {
    @Override
    protected String getBusiness() {
        return "CL041";
    }
}
