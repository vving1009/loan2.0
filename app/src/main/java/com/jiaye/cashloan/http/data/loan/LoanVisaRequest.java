package com.jiaye.cashloan.http.data.loan;

import com.jiaye.cashloan.http.base.ChildRequest;

/**
 * LoanVisaRequest
 *
 * @author 贾博瑄
 */

public class LoanVisaRequest extends ChildRequest {

    @Override
    protected String getBusiness() {
        return "CL053";
    }
}
