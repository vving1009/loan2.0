package com.jiaye.cashloan.http.data.loan;

import com.jiaye.cashloan.http.base.ChildRequest;

/**
 * ContractRequest
 *
 * @author 贾博瑄
 */

public class ContractRequest extends ChildRequest {

    @Override
    protected String getBusiness() {
        return "CL040";
    }
}
