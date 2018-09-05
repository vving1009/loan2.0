package com.jiaye.cashloan.http.data.step2;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildResponse;

/**
 * Step2
 *
 * @author 贾博瑄
 */
public class Step2 extends SatcatcheChildResponse {

    /**
     * carinsurance_auth : 1 (0否1是)
     * taobao_auth : 1
     *  bioassay_auth  : 1
     *  operator_auth  : 1
     *  user_info  : 1
     *  car_papers  : 1
     */

    @SerializedName("carinsurance_auth")
    private int carinsuranceAuth;
    @SerializedName("idcard_auth")
    private int id;
    @SerializedName("taobao_auth")
    private int taobaoAuth;
    @SerializedName("bioassay_auth")
    private int bioassayAuth;
    @SerializedName("operator_auth")
    private int operatorAuth;
    @SerializedName("user_info")
    private int userInfo;
    @SerializedName("car_papers")
    private int carPapers;

    public int getCarinsuranceAuth() {
        return carinsuranceAuth;
    }

    public void setCarinsuranceAuth(int carinsuranceAuth) {
        this.carinsuranceAuth = carinsuranceAuth;
    }

    public int getTaobaoAuth() {
        return taobaoAuth;
    }

    public void setTaobaoAuth(int taobaoAuth) {
        this.taobaoAuth = taobaoAuth;
    }

    public int getBioassayAuth() {
        return bioassayAuth;
    }

    public void setBioassayAuth(int bioassayAuth) {
        this.bioassayAuth = bioassayAuth;
    }

    public int getOperatorAuth() {
        return operatorAuth;
    }

    public void setOperatorAuth(int operatorAuth) {
        this.operatorAuth = operatorAuth;
    }

    public int getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(int userInfo) {
        this.userInfo = userInfo;
    }

    public int getCarPapers() {
        return carPapers;
    }

    public void setCarPapers(int carPapers) {
        this.carPapers = carPapers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
