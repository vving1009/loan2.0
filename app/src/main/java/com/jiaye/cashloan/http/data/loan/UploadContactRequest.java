package com.jiaye.cashloan.http.data.loan;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * UploadRequest
 *
 * @author 贾博瑄
 */

public class UploadContactRequest {

    @SerializedName("login_name")
    private String phone;

    @SerializedName("data")
    private List<Contact> contacts;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public static class Contact {

        @SerializedName("contacts_name")
        private String name;

        @SerializedName("phone_number")
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
