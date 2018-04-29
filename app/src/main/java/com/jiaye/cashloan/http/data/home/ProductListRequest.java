package com.jiaye.cashloan.http.data.home;

import com.jiaye.cashloan.http.base.ChildRequest;

/**
 * ProductListRequest
 *
 * @author 贾博瑄
 */
public class ProductListRequest extends ChildRequest {

    @Override
    protected String getBusiness() {
        return "CL005";
    }
}
