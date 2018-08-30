package com.jiaye.cashloan.http.data.loan;

import com.jiaye.cashloan.http.base.ChildRequest;

public class TermsAuthRequest extends ChildRequest {
    @Override
    protected String getBusiness() {
        return "CL080";
    }
}
