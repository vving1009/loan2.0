package com.jiaye.cashloan.http.data.loan;

import com.jiaye.cashloan.http.base.ChildRequest;

/**
 * LoanConfirmInfoRequest
 *
 * @author 贾博瑄
 */

public class LoanConfirmInfoRequest extends ChildRequest {

    @Override
    protected String getBusiness() {
        return "CL024";
    }
}
