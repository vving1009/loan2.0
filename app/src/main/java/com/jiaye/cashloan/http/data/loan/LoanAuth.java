package com.jiaye.cashloan.http.data.loan;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.ChildResponse;

/**
 * LoanAuth
 *
 * @author 贾博瑄
 */

public class LoanAuth extends ChildResponse {

    /*产品编号*/
    @SerializedName("jla_id")
    private String productId;

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
}
