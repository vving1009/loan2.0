package com.jiaye.cashloan.http.data.my;

import com.jiaye.cashloan.http.base.ChildRequest;

/**
 * UserRequest
 *
 * @author 贾博瑄
 */

public class UserRequest extends ChildRequest {

    @Override
    protected String getBusiness() {
        return "CL023";
    }
}
