package com.jiaye.cashloan.http.data.auth;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildResponse;

/**
 * Auth
 *
 * @author 贾博瑄
 */

public class Auth extends SatcatcheChildResponse {

    /* 银行卡认证状态*/
    @SerializedName("bindBank_state")
    private int bankState;

    /*身份证认证状态*/
    @SerializedName("cardAuth_state")
    private int cardState;

    /*活体识别认证状态*/
    @SerializedName("faceCheak_state")
    private int faceState;

    /*个人资料认证状态*/
    @SerializedName("userAuth_state")
    private int personState;

    /*手机运营商认证状态*/
    @SerializedName("phoneAuth_state")
    private int phoneState;

    /*淘宝认证状态*/
    @SerializedName("taoAuth_state")
    private int taobaoState;

    /*手机号*/
    @SerializedName("phone")
    private String phone;

    public int getBankState() {
        return bankState;
    }

    public void setBankState(int bankState) {
        this.bankState = bankState;
    }

    public int getCardState() {
        return cardState;
    }

    public void setCardState(int cardState) {
        this.cardState = cardState;
    }

    public int getFaceState() {
        return faceState;
    }

    public void setFaceState(int faceState) {
        this.faceState = faceState;
    }

    public int getPersonState() {
        return personState;
    }

    public void setPersonState(int personState) {
        this.personState = personState;
    }

    public int getPhoneState() {
        return phoneState;
    }

    public void setPhoneState(int phoneState) {
        this.phoneState = phoneState;
    }

    public int getTaobaoState() {
        return taobaoState;
    }

    public void setTaobaoState(int taobaoState) {
        this.taobaoState = taobaoState;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
