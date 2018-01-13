package com.jiaye.cashloan.http.data.loan;

import com.jiaye.cashloan.http.base.ChildRequest;

/**
 * LoanVisaSMSRequest
 *
 * @author 贾博瑄
 */

public class LoanVisaSMSRequest extends ChildRequest {

    @Override
    protected String getBusiness() {
        return "CL054";
    }
}
