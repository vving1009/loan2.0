package com.jiaye.cashloan.http.data.step1;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildResponse;

/**
 * Step1
 *
 * @author 贾博瑄
 */
public class Step1 extends SatcatcheChildResponse {

    @SerializedName("idcard_auth")
    private int id;

    @SerializedName("bioassay_auth")
    private int bioassay;

    @SerializedName("user_info")
    private int personal;

    @SerializedName("operator_auth")
    private int phone;

    @SerializedName("car_papers")
    private int car;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBioassay() {
        return bioassay;
    }

    public void setBioassay(int bioassay) {
        this.bioassay = bioassay;
    }

    public int getPersonal() {
        return personal;
    }

    public void setPersonal(int personal) {
        this.personal = personal;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public int getCar() {
        return car;
    }

    public void setCar(int car) {
        this.car = car;
    }
}
