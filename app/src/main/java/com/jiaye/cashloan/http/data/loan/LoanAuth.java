package com.jiaye.cashloan.http.data.loan;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildResponse;

/**
 * LoanAuth
 *
 * @author 贾博瑄
 */

public class LoanAuth extends SatcatcheChildResponse {

    /*借款编号*/
    @SerializedName("jla_id")
    private String loanId;

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

    /*身份证姓名*/
    @SerializedName("jcb_name")
    private String ocrName;

    /*身份证姓名*/
    @SerializedName("jcb_identifyid")
    private String ocrID;

    /*手机号*/
    @SerializedName("jcb_phone")
    private String phone;

    /*电子签章*/
    @SerializedName("eSign_state")
    private String signState;

    /*是否签署过电子签章*/
    @SerializedName("signCompact_state")
    private String hasSign;

    /*历史电子签章*/
    @SerializedName("eSignJK_state")
    private String signHistoryState;

    /*是否需要历史电子签章*/
    @SerializedName("mustSignJK_state")
    private String needSignHistory;

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
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

    public String getSignState() {
        return signState;
    }

    public void setSignState(String signState) {
        this.signState = signState;
    }

    public String getHasSign() {
        return hasSign;
    }

    public void setHasSign(String hasSign) {
        this.hasSign = hasSign;
    }

    public String getSignHistoryState() {
        return signHistoryState;
    }

    public void setSignHistoryState(String signHistoryState) {
        this.signHistoryState = signHistoryState;
    }

    public String getNeedSignHistory() {
        return needSignHistory;
    }

    public void setNeedSignHistory(String needSignHistory) {
        this.needSignHistory = needSignHistory;
    }
}
