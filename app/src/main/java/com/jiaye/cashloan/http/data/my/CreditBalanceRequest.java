package com.jiaye.cashloan.http.data.my;

import com.jiaye.cashloan.http.base.ChildRequest;

/**
 * CreditBalanceRequest
 *
 * @author 贾博瑄
 */

public class CreditBalanceRequest extends ChildRequest {

    @Override
    protected String getBusiness() {
        return "CL048";
    }
}
