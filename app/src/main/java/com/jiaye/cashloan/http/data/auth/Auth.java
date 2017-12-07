package com.jiaye.cashloan.http.data.auth;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.ChildResponse;

/**
 * Auth
 *
 * @author 贾博瑄
 */

public class Auth extends ChildResponse {

    /*产品编号*/
    @SerializedName("jla_id")
    private String productId;

    /* 银行卡认证状态*/
    @SerializedName("bindBank_state")
    private String bankState;

    /*身份证认证状态*/
    @SerializedName("cardAuth_state")
    private String cardState;

    /*活体识别认证状态*/
    @SerializedName("faceCheak_state")
    private String faceState;

    /*个人资料认证状态*/
    @SerializedName("userAuth_state")
    private String personState;

    /*手机运营商认证状态*/
    @SerializedName("phoneAuth_state")
    private String phoneState;

    /*淘宝认证状态*/
    @SerializedName("taoAuth_state")
    private String taobaoState;

    /*芝麻信用认证状态*/
    @SerializedName("zmAuth_state")
    private String sesameState;

    /*芝麻信用分*/
    @SerializedName("zm_score")
    private String sesame;

    /*身份证姓名*/
    @SerializedName("jcb_name")
    private String ocrName;

    /*身份证姓名*/
    @SerializedName("jcb_identifyid")
    private String ocrID;

    /*手机号*/
    @SerializedName("jcb_phone")
    private String phone;

    public String getBankState() {
        return bankState;
    }

    public void setBankState(String bankState) {
        this.bankState = bankState;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCardState() {
        return cardState;
    }

    public void setCardState(String cardState) {
        this.cardState = cardState;
    }

    public String getFaceState() {
        return faceState;
    }

    public void setFaceState(String faceState) {
        this.faceState = faceState;
    }

    public String getPersonState() {
        return personState;
    }

    public void setPersonState(String personState) {
        this.personState = personState;
    }

    public String getPhoneState() {
        return phoneState;
    }

    public void setPhoneState(String phoneState) {
        this.phoneState = phoneState;
    }

    public String getTaobaoState() {
        return taobaoState;
    }

    public void setTaobaoState(String taobaoState) {
        this.taobaoState = taobaoState;
    }

    public String getSesameState() {
        return sesameState;
    }

    public void setSesameState(String sesameState) {
        this.sesameState = sesameState;
    }

    public String getSesame() {
        return sesame;
    }

    public void setSesame(String sesame) {
        this.sesame = sesame;
    }

    public String getOcrName() {
        return ocrName;
    }

    public void setOcrName(String ocrName) {
        this.ocrName = ocrName;
    }

    public String getOcrID() {
        return ocrID;
    }

    public void setOcrID(String ocrID) {
        this.ocrID = ocrID;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
