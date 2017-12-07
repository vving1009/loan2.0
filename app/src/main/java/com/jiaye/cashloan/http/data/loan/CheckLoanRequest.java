package com.jiaye.cashloan.http.data.loan;

import com.jiaye.cashloan.http.base.ChildRequest;

/**
 * CheckLoanRequest
 *
 * @author 贾博瑄
 */

public class CheckLoanRequest extends ChildRequest {

    @Override
    protected String getBusiness() {
        return "CL043";
    }
}
