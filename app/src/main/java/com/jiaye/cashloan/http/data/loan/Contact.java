package com.jiaye.cashloan.http.data.loan;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.ChildResponse;

/**
 * Contact
 *
 * @author 贾博瑄
 */

public class Contact extends ChildResponse {

    @SerializedName("link_data")
    private ContactData[] data;

    public ContactData[] getData() {
        return data;
    }

    public void setData(ContactData[] data) {
        this.data = data;
    }
}
