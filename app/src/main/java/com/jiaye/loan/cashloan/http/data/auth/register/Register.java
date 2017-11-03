package com.jiaye.loan.cashloan.http.data.auth.register;

import com.jiaye.loan.cashloan.http.base.ChildResponse;

/**
 * Register
 *
 * @author 贾博瑄
 */

public class Register extends ChildResponse {

    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
