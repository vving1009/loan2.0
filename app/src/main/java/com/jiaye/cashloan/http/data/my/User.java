package com.jiaye.cashloan.http.data.my;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildResponse;

/**
 * User
 *
 * @author 贾博瑄
 */

public class User extends SatcatcheChildResponse implements Parcelable {

    /*姓名[服务器返回]*/
    @SerializedName("jcb_name")
    private String name;

    /*身份证号码[服务器返回]*/
    @SerializedName("id_number")
    private String id;

    /*手机号*/
    private String phone;

    /*借贷审批次数*/
    @SerializedName("loan_approval")
    private int approveNumber;

    /*放款还贷次数*/
    @SerializedName("loan_repayment")
    private int progressNumber;

    /*历史借贷次数*/
    @SerializedName("loan_history")
    private int historyNumber;

    /*前台实际显示的姓名信息
    1.没有姓名及手机号时,显示 "游客".
    2.没有姓名只有手机号时,显示 "137****6666"
    3.有姓名时显示 "*名"*/
    private String showName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getApproveNumber() {
        return approveNumber;
    }

    public void setApproveNumber(int approveNumber) {
        this.approveNumber = approveNumber;
    }

    public int getProgressNumber() {
        return progressNumber;
    }

    public void setProgressNumber(int progressNumber) {
        this.progressNumber = progressNumber;
    }

    public int getHistoryNumber() {
        return historyNumber;
    }

    public void setHistoryNumber(int historyNumber) {
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
        dest.writeString(this.name);
        dest.writeString(this.id);
        dest.writeString(this.phone);
        dest.writeInt(this.approveNumber);
        dest.writeInt(this.progressNumber);
        dest.writeInt(this.historyNumber);
        dest.writeString(this.showName);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.name = in.readString();
        this.id = in.readString();
        this.phone = in.readString();
        this.approveNumber = in.readInt();
        this.progressNumber = in.readInt();
        this.historyNumber = in.readInt();
        this.showName = in.readString();
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
