package com.jiaye.cashloan.http.data.loan;

import com.jiaye.cashloan.http.base.ChildRequest;

/**
 * ContactRequest
 *
 * @author 贾博瑄
 */

public class ContactRequest extends ChildRequest {

    @Override
    protected String getBusiness() {
        return "CL015";
    }
}
