package com.jiaye.cashloan.persistence;

import android.os.Parcel;
import android.os.Parcelable;

public class Salesman implements Parcelable {

    private String companyId;

    private String company;

    private String name;

    private String workId;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.companyId);
        dest.writeString(this.company);
        dest.writeString(this.name);
        dest.writeString(this.workId);
    }

    public Salesman() {
    }

    protected Salesman(Parcel in) {
        this.companyId = in.readString();
        this.company = in.readString();
        this.name = in.readString();
        this.workId = in.readString();
    }

    public static final Creator<Salesman> CREATOR = new Creator<Salesman>() {
        @Override
        public Salesman createFromParcel(Parcel source) {
            return new Salesman(source);
        }

        @Override
        public Salesman[] newArray(int size) {
            return new Salesman[size];
        }
    };
}
