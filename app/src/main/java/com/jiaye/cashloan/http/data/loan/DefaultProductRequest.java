package com.jiaye.cashloan.http.data.loan;

import com.jiaye.cashloan.http.base.ChildRequest;

/**
 * DefaultProductRequest
 *
 * @author 贾博瑄
 */

public class DefaultProductRequest extends ChildRequest {

    @Override
    protected String getBusiness() {
        return "CL002";
    }
}
