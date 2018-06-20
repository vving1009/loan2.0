package com.jiaye.cashloan.http.data.sms;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildRequest;

import java.util.List;

public class UploadSmsRequest extends SatcatcheChildRequest {

    @SerializedName("phone")
    private String phone;

    @SerializedName("jla_id")
    private String loanId;

    @SerializedName("authSmsInfo")
    private List<SmsInfo> smsInfos;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public List<SmsInfo> getSmsInfos() {
        return smsInfos;
    }

    public void setSmsInfos(List<SmsInfo> authSmsInfo) {
        this.smsInfos = authSmsInfo;
    }

    public static class SmsInfo {

        @SerializedName("phone_num")
        private String phoneNum;

        @SerializedName("message")
        private String message;

        public String getPhoneNum() {
            return phoneNum;
        }

        public void setPhoneNum(String phoneNum) {
            this.phoneNum = phoneNum;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    @Override
    protected String getBusiness() {
        return "CL026";
    }
}
