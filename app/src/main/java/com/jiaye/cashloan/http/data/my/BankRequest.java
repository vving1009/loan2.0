package com.jiaye.cashloan.http.data.my;

import com.jiaye.cashloan.http.base.ChildRequest;

/**
 * BankRequest
 *
 * @author 贾博瑄
 */

public class BankRequest extends ChildRequest {

    @Override
    protected String getBusiness() {
        return "CL017";
    }
}
