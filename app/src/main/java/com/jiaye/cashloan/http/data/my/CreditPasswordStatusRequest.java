package com.jiaye.cashloan.http.data.my;

import com.jiaye.cashloan.http.base.ChildRequest;

/**
 * CreditPasswordStatusRequest
 *
 * @author 贾博瑄
 */

public class CreditPasswordStatusRequest extends ChildRequest {

    @Override
    protected String getBusiness() {
        return "CL050";
    }
}
