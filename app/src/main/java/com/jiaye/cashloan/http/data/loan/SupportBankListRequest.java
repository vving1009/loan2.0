package com.jiaye.cashloan.http.data.loan;

import com.jiaye.cashloan.http.base.ChildRequest;

/**
 * SupportBankListRequest
 *
 * @author 贾博瑄
 */

public class SupportBankListRequest extends ChildRequest {

    @Override
    protected String getBusiness() {
        return "CL063";
    }
}
