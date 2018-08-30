package com.jiaye.cashloan.http.data.my;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.ChildResponse;

/**
 * CreditInfo
 *
 * @author 贾博瑄
 */

public class CreditInfo extends ChildResponse implements Parcelable {

    @SerializedName("account_id")
    private String accountId;

    @SerializedName("id_card")
    private String id;

    @SerializedName("mobile")
    private String phone;

    @SerializedName("real_name")
    private String name;

    /*01-未开户;02-已开户未绑卡;03-已开户已绑卡*/
    @SerializedName("bankcard_status")
    private String bankStatus;

    @SerializedName("card_num")
    private String bankNo;

    @SerializedName("account_name")
    private String accountName;

    /**
     * 密码设置状态 （0否1是）
     */
    @SerializedName("pwd_status")
    private String pwdStatus;

    /**
     * 缴费授权有效期 格式：yyyy-MM-dd
     */
    @SerializedName("payment_deadline")
    private String paymentDeadline;

    /**
     * 缴费授权金额
     */
    @SerializedName("payment_maxamt")
    private String paymentMaxamt;

    /**
     * 还款授权有效期 格式：yyyy-MM-dd
     */
    @SerializedName("repayment_deadline")
    private String repaymentDeadline;

    /**
     * 还款授权金额
     */
    @SerializedName("repayment_maxamt")
    private String repaymentMaxamt;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBankStatus() {
        return bankStatus;
    }

    public void setBankStatus(String bankStatus) {
        this.bankStatus = bankStatus;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPwdStatus() {
        return pwdStatus;
    }

    public void setPwdStatus(String pwdStatus) {
        this.pwdStatus = pwdStatus;
    }

    public String getPaymentDeadline() {
        return paymentDeadline;
    }

    public void setPaymentDeadline(String paymentDeadline) {
        this.paymentDeadline = paymentDeadline;
    }

    public String getPaymentMaxamt() {
        return paymentMaxamt;
    }

    public void setPaymentMaxamt(String paymentMaxamt) {
        this.paymentMaxamt = paymentMaxamt;
    }

    public String getRepaymentDeadline() {
        return repaymentDeadline;
    }

    public void setRepaymentDeadline(String repaymentDeadline) {
        this.repaymentDeadline = repaymentDeadline;
    }

    public String getRepaymentMaxamt() {
        return repaymentMaxamt;
    }

    public void setRepaymentMaxamt(String repaymentMaxamt) {
        this.repaymentMaxamt = repaymentMaxamt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.accountId);
        dest.writeString(this.id);
        dest.writeString(this.phone);
        dest.writeString(this.name);
        dest.writeString(this.bankStatus);
        dest.writeString(this.bankNo);
        dest.writeString(this.accountName);
        dest.writeString(this.pwdStatus);
        dest.writeString(this.paymentDeadline);
        dest.writeString(this.paymentMaxamt);
        dest.writeString(this.repaymentDeadline);
        dest.writeString(this.repaymentMaxamt);
    }

    public CreditInfo() {
    }

    protected CreditInfo(Parcel in) {
        this.accountId = in.readString();
        this.id = in.readString();
        this.phone = in.readString();
        this.name = in.readString();
        this.bankStatus = in.readString();
        this.bankNo = in.readString();
        this.accountName = in.readString();
        this.pwdStatus = in.readString();
        this.paymentDeadline = in.readString();
        this.paymentMaxamt = in.readString();
        this.repaymentDeadline = in.readString();
        this.repaymentMaxamt = in.readString();
    }

    public static final Parcelable.Creator<CreditInfo> CREATOR = new Parcelable.Creator<CreditInfo>() {
        @Override
        public CreditInfo createFromParcel(Parcel source) {
            return new CreditInfo(source);
        }

        @Override
        public CreditInfo[] newArray(int size) {
            return new CreditInfo[size];
        }
    };
}
