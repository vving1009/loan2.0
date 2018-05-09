package com.jiaye.cashloan.http.data.loan;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildRequest;

import java.util.List;

/**
 * UploadRequest
 *
 * @author 贾博瑄
 */

public class UploadContactRequest extends SatcatcheChildRequest {

    @SerializedName("contacts_list")
    private List<Contact> contacts;

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    protected String getBusiness() {
        return "CL088";
    }

    public static class Contact {

        @SerializedName("contacts_name")
        private String name;

        @SerializedName("contacts_phone")
        private String phone;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}
