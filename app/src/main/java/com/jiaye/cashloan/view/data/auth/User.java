package com.jiaye.cashloan.view.data.auth;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * User
 *
 * @author 贾博瑄
 */

public class User implements Parcelable {

    /*令牌*/
    private String token;

    /*姓名*/
    private String name;

    /*手机号*/
    private String phone;

    /*借贷审批次数*/
    private String approveNumber;

    /*放款还贷次数*/
    private String loanNumber;

    /*历史借贷次数*/
    private String historyNumber;

    /*前台实际显示的姓名信息
    1.没有姓名及手机号时,显示 "游客".
    2.没有姓名只有手机号时,显示 "137****6666"
    3.有姓名时显示 "*名"*/
    private String showName;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

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

    public String getApproveNumber() {
        return approveNumber;
    }

    public void setApproveNumber(String approveNumber) {
        this.approveNumber = approveNumber;
    }

    public String getLoanNumber() {
        return loanNumber;
    }

    public void setLoanNumber(String loanNumber) {
        this.loanNumber = loanNumber;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.token);
        dest.writeString(this.name);
        dest.writeString(this.phone);
        dest.writeString(this.approveNumber);
        dest.writeString(this.loanNumber);
        dest.writeString(this.historyNumber);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.token = in.readString();
        this.name = in.readString();
        this.phone = in.readString();
        this.approveNumber = in.readString();
        this.loanNumber = in.readString();
        this.historyNumber = in.readString();
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
