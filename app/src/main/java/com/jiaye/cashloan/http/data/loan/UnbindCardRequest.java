package com.jiaye.cashloan.http.data.loan;

import com.jiaye.cashloan.http.base.ChildRequest;

public class UnbindCardRequest extends ChildRequest {
    @Override
    protected String getBusiness() {
        return "CL061";
    }
}
