package com.jiaye.cashloan.view.data.my.credit;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * CreditCashRequest
 *
 * @author 贾博瑄
 */

public class CreditCashRequest extends BaseCreditRequest implements Parcelable {

    @SerializedName("cardNo")
    private String cardNo;

    @SerializedName("txAmount")
    private String txAmount;

    @SerializedName("txFee")
    private String txFee;

    @SerializedName("routeCode")
    private String routeCode;

    @SerializedName("cardBankCnaps")
    private String cardBankCnaps;

    @SerializedName("cardBankCode")
    private String cardBankCode;

    @SerializedName("cardBankNameCn")
    private String cardBankNameCn;

    @SerializedName("cardBankNameEn")
    private String cardBankNameEn;

    @SerializedName("cardBankProvince")
    private String cardBankProvince;

    @SerializedName("cardBankCity")
    private String cardBankCity;

    @SerializedName("forgotPwdUrl")
    private String forgotPwdUrl;

    public CreditCashRequest() {
        txCode = "withdraw";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.cardNo);
        dest.writeString(this.txAmount);
        dest.writeString(this.txFee);
        dest.writeString(this.routeCode);
        dest.writeString(this.cardBankCnaps);
        dest.writeString(this.cardBankCode);
        dest.writeString(this.cardBankNameCn);
        dest.writeString(this.cardBankNameEn);
        dest.writeString(this.cardBankProvince);
        dest.writeString(this.cardBankCity);
        dest.writeString(this.forgotPwdUrl);
        dest.writeString(this.version);
        dest.writeString(this.txCode);
        dest.writeString(this.instCode);
        dest.writeString(this.bankCode);
        dest.writeString(this.txDate);
        dest.writeString(this.txTime);
        dest.writeInt(this.seqNo);
        dest.writeString(this.channel);
        dest.writeString(this.accountId);
        dest.writeString(this.idType);
        dest.writeString(this.idNo);
        dest.writeString(this.name);
        dest.writeString(this.mobile);
        dest.writeString(this.retUrl);
        dest.writeString(this.notifyUrl);
        dest.writeString(this.acqRes);
        dest.writeString(this.sign);
    }

    protected CreditCashRequest(Parcel in) {
        this.cardNo = in.readString();
        this.txAmount = in.readString();
        this.txFee = in.readString();
        this.routeCode = in.readString();
        this.cardBankCnaps = in.readString();
        this.cardBankCode = in.readString();
        this.cardBankNameCn = in.readString();
        this.cardBankNameEn = in.readString();
        this.cardBankProvince = in.readString();
        this.cardBankCity = in.readString();
        this.forgotPwdUrl = in.readString();
        this.version = in.readString();
        this.txCode = in.readString();
        this.instCode = in.readString();
        this.bankCode = in.readString();
        this.txDate = in.readString();
        this.txTime = in.readString();
        this.seqNo = in.readInt();
        this.channel = in.readString();
        this.accountId = in.readString();
        this.idType = in.readString();
        this.idNo = in.readString();
        this.name = in.readString();
        this.mobile = in.readString();
        this.retUrl = in.readString();
        this.notifyUrl = in.readString();
        this.acqRes = in.readString();
        this.sign = in.readString();
    }

    public static final Parcelable.Creator<CreditCashRequest> CREATOR = new Parcelable.Creator<CreditCashRequest>() {
        @Override
        public CreditCashRequest createFromParcel(Parcel source) {
            return new CreditCashRequest(source);
        }

        @Override
        public CreditCashRequest[] newArray(int size) {
            return new CreditCashRequest[size];
        }
    };
}
