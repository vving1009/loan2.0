package com.jiaye.cashloan.view.data.my.credit;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.utils.DateUtil;
import com.jiaye.cashloan.utils.RandomUtil;

/**
 * BaseCreditRequest
 *
 * @author 贾博瑄
 */

public abstract class BaseCreditRequest {

    @SerializedName("version")
    protected String version = "10";

    @SerializedName("txCode")
    protected String txCode;

    @SerializedName("instCode")
    protected String instCode = "01380001";

    @SerializedName("bankCode")
    protected String bankCode = "30050000";

    @SerializedName("txDate")
    protected String txDate = DateUtil.formatDate(System.currentTimeMillis());

    @SerializedName("txTime")
    protected String txTime = DateUtil.formatTime(System.currentTimeMillis());

    @SerializedName("seqNo")
    protected int seqNo = Integer.parseInt(RandomUtil.number6());

    @SerializedName("channel")
    protected String channel = "000001";

    @SerializedName("accountId")
    protected String accountId = "1";

    @SerializedName("idType")
    protected String idType = "01";

    @SerializedName("idNo")
    protected String idNo;

    @SerializedName("name")
    protected String name;

    @SerializedName("mobile")
    protected String mobile;

    @SerializedName("retUrl")
    protected String retUrl;

    @SerializedName("notifyUrl")
    protected String notifyUrl;

    @SerializedName("acqRes")
    protected String acqRes;

    @SerializedName("sign")
    protected String sign;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTxCode() {
        return txCode;
    }

    public void setTxCode(String txCode) {
        this.txCode = txCode;
    }

    public String getInstCode() {
        return instCode;
    }

    public void setInstCode(String instCode) {
        this.instCode = instCode;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getTxDate() {
        return txDate;
    }

    public void setTxDate(String txDate) {
        this.txDate = txDate;
    }

    public String getTxTime() {
        return txTime;
    }

    public void setTxTime(String txTime) {
        this.txTime = txTime;
    }

    public int getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(int seqNo) {
        this.seqNo = seqNo;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRetUrl() {
        return retUrl;
    }

    public void setRetUrl(String retUrl) {
        this.retUrl = retUrl;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getAcqRes() {
        return acqRes;
    }

    public void setAcqRes(String acqRes) {
        this.acqRes = acqRes;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
