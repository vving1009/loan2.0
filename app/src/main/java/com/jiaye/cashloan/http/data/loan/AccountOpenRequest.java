package com.jiaye.cashloan.http.data.loan;

import com.jiaye.cashloan.http.base.ChildRequest;

public class AccountOpenRequest extends ChildRequest {
    @Override
    protected String getBusiness() {
        return "CL069";
    }
}
