package com.jiaye.cashloan.http.data.my;

import com.jiaye.cashloan.http.base.ChildRequest;

/**
 * CreditUnBindBankRequest
 *
 * @author 贾博瑄
 */

public class CreditUnBindBankRequest extends ChildRequest {

    @Override
    protected String getBusiness() {
        return "CL061";
    }
}
