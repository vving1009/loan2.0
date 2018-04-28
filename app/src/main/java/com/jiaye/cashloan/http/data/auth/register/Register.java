package com.jiaye.cashloan.http.data.auth.register;

import com.jiaye.cashloan.http.base.SatcatcheChildResponse;

/**
 * Register
 *
 * @author 贾博瑄
 */

public class Register extends SatcatcheChildResponse {

    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
