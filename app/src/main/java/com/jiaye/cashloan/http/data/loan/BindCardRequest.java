package com.jiaye.cashloan.http.data.loan;

import com.jiaye.cashloan.http.base.ChildRequest;

public class BindCardRequest extends ChildRequest {
    @Override
    protected String getBusiness() {
        return "CL070";
    }
}
