package com.jiaye.cashloan.http.data.my;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.ChildResponse;

/**
 * User
 *
 * @author 贾博瑄
 */

public class User extends ChildResponse implements Parcelable {

    /*姓名*/
    @SerializedName("jcb_name")
    private String name;

    /*借贷审批次数*/
    @SerializedName("loan_approval")
    private String approveNumber;

    /*放款还贷次数*/
    @SerializedName("loan_repayment")
    private String progressNumber;

    /*历史借贷次数*/
    @SerializedName("loan_history")
    private String historyNumber;

    /*前台实际显示的姓名信息
    1.没有姓名及手机号时,显示 "游客".
    2.没有姓名只有手机号时,显示 "137****6666"
    3.有姓名时显示 "*名"*/
    private String showName;

    /*手机号*/
    private String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApproveNumber() {
        return approveNumber;
    }

    public void setApproveNumber(String approveNumber) {
        this.approveNumber = approveNumber;
    }

    public String getProgressNumber() {
        return progressNumber;
    }

    public void setProgressNumber(String progressNumber) {
        this.progressNumber = progressNumber;
    }

    public String getHistoryNumber() {
        return historyNumber;
    }

    public void setHistoryNumber(String historyNumber) {
        this.historyNumber = historyNumber;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.approveNumber);
        dest.writeString(this.progressNumber);
        dest.writeString(this.historyNumber);
        dest.writeString(this.showName);
        dest.writeString(this.phone);
        dest.writeString(getSerialnumber());
        dest.writeString(getToken());
        dest.writeString(getDeviceId());
    }

    public User() {
    }

    protected User(Parcel in) {
        this.name = in.readString();
        this.approveNumber = in.readString();
        this.progressNumber = in.readString();
        this.historyNumber = in.readString();
        this.showName = in.readString();
        this.phone = in.readString();
        setSerialnumber(in.readString());
        setToken(in.readString());
        setDeviceId(in.readString());
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
