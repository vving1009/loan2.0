package com.jiaye.cashloan.http.data.auth.login;

import com.jiaye.cashloan.http.base.ChildResponse;

/**
 * Login
 *
 * @author 贾博瑄
 */

public class Login extends ChildResponse {

    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
