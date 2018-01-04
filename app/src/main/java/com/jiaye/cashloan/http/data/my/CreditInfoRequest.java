package com.jiaye.cashloan.http.data.my;

import com.jiaye.cashloan.http.base.ChildRequest;

/**
 * CreditInfoRequest
 *
 * @author 贾博瑄
 */

public class CreditInfoRequest extends ChildRequest {

    @Override
    protected String getBusiness() {
        return "CL051";
    }
}
