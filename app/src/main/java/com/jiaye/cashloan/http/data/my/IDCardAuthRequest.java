package com.jiaye.cashloan.http.data.my;

import com.jiaye.cashloan.http.base.ChildRequest;

/**
 * IDCardAuthRequest
 *
 * @author 贾博瑄
 */

public class IDCardAuthRequest extends ChildRequest {

    @Override
    protected String getBusiness() {
        return "CL041";
    }
}
