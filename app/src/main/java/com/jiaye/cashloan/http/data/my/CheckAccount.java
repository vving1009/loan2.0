package com.jiaye.cashloan.http.data.my;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildResponse;

/**
 * CheckAccount
 *
 * @author 贾博瑄
 */
public class CheckAccount extends SatcatcheChildResponse {

    @SerializedName("whether_openanaccount")
    private int isOpen;

    public int getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(int isOpen) {
        this.isOpen = isOpen;
    }
}
