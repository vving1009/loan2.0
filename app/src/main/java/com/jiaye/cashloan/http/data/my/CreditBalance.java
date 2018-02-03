package com.jiaye.cashloan.http.data.my;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.ChildResponse;

/**
 * CreditBalance
 *
 * @author 贾博瑄
 */

public class CreditBalance extends ChildResponse implements Parcelable {

    /*可用余额*/
    @SerializedName("avail_bal")
    private String availBal;

    /*冻结余额*/
    @SerializedName("freeze_bal")
    private String freezeBal;

    /*账面余额*/
    @SerializedName("curr_bal")
    private String currBal;

    /*支行名称*/
    @SerializedName("account_bank")
    private String bankName;

    /*银行卡号*/
    @SerializedName("bank_no")
    private String bankNo;

    /*银联号*/
    @SerializedName("bank_cnapsno")
    private String bankCnapsno;

    /*是否可提现*/
    @SerializedName("withDrawStatus")
    private String isSupportCash = "0";

    public String getAvailBal() {
        return availBal;
    }

    public void setAvailBal(String availBal) {
        this.availBal = availBal;
    }

    public String getFreezeBal() {
        return freezeBal;
    }

    public void setFreezeBal(String freezeBal) {
        this.freezeBal = freezeBal;
    }

    public String getCurrBal() {
        return currBal;
    }

    public void setCurrBal(String currBal) {
        this.currBal = currBal;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getBankCnapsno() {
        return bankCnapsno;
    }

    public void setBankCnapsno(String bankCnapsno) {
        this.bankCnapsno = bankCnapsno;
    }

    public String getIsSupportCash() {
        return isSupportCash;
    }

    public void setIsSupportCash(String isSupportCash) {
        this.isSupportCash = isSupportCash;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.availBal);
        dest.writeString(this.freezeBal);
        dest.writeString(this.currBal);
        dest.writeString(this.bankName);
        dest.writeString(this.bankNo);
        dest.writeString(this.bankCnapsno);
        dest.writeString(this.isSupportCash);
    }

    public CreditBalance() {
    }

    protected CreditBalance(Parcel in) {
        this.availBal = in.readString();
        this.freezeBal = in.readString();
        this.currBal = in.readString();
        this.bankName = in.readString();
        this.bankNo = in.readString();
        this.bankCnapsno = in.readString();
        this.isSupportCash = in.readString();
    }

    public static final Parcelable.Creator<CreditBalance> CREATOR = new Parcelable.Creator<CreditBalance>() {
        @Override
        public CreditBalance createFromParcel(Parcel source) {
            return new CreditBalance(source);
        }

        @Override
        public CreditBalance[] newArray(int size) {
            return new CreditBalance[size];
        }
    };
}
