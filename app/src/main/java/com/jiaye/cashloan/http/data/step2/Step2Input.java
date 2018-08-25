package com.jiaye.cashloan.http.data.step2;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildResponse;

/**
 * Step2Input
 *
 * @author 贾博瑄
 */
public class Step2Input extends SatcatcheChildResponse {

    @SerializedName("idcard_auth")
    private int insurance;

    @SerializedName("bioassay_auth")
    private int bioassay;

    @SerializedName("user_info")
    private int personal;

    @SerializedName("operator_auth")
    private int phone;

    @SerializedName("taobao")
    private int taobao;

    @SerializedName("car_papers")
    private int car;

    public int getInsurance() {
        return insurance;
    }

    public void setInsurance(int insurance) {
        this.insurance = insurance;
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

    public int getTaobao() {
        return taobao;
    }

    public void setTaobao(int taobao) {
        this.taobao = taobao;
    }
}
