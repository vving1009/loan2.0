package com.jiaye.cashloan.view.data.my.credit;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * CreditBalanceRequest
 *
 * @author 贾博瑄
 */

public class CreditBalanceRequest extends BaseCreditRequest implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
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

    public CreditBalanceRequest() {
    }

    protected CreditBalanceRequest(Parcel in) {
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

    public static final Parcelable.Creator<CreditBalanceRequest> CREATOR = new Parcelable.Creator<CreditBalanceRequest>() {
        @Override
        public CreditBalanceRequest createFromParcel(Parcel source) {
            return new CreditBalanceRequest(source);
        }

        @Override
        public CreditBalanceRequest[] newArray(int size) {
            return new CreditBalanceRequest[size];
        }
    };
}
