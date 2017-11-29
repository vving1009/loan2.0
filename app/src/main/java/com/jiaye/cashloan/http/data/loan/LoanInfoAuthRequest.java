package com.jiaye.cashloan.http.data.loan;

import com.jiaye.cashloan.http.base.ChildRequest;

/**
 * LoanInfoAuthRequest
 *
 * @author 贾博瑄
 */

public class LoanInfoAuthRequest extends ChildRequest {

    @Override
    protected String getBusiness() {
        return "CL039";
    }
}
