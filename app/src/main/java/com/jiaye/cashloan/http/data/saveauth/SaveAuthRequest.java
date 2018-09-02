package com.jiaye.cashloan.http.data.saveauth;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildRequest;

public class SaveAuthRequest extends SatcatcheChildRequest {


    /**
     * jla_id : A006201805101842424408
     * marriage_msg : 未婚
     * edu_msg : 大学
     * work_industry : it
     * work_number : 10年
     * work_post : it
     * monthly_income : 10000
     * house_property : 房产
     * credit_card_num : 1
     * credit_card_quota : 100000
     * loan_describe : 描述
     */

    @SerializedName("jla_id")
    private String jlaId;
    @SerializedName("marriage_msg")
    private String marriageMsg;
    @SerializedName("edu_msg")
    private String eduMsg;
    @SerializedName("work_industry")
    private String workIndustry;
    @SerializedName("work_number")
    private String workNumber;
    @SerializedName("work_post")
    private String workPost;
    @SerializedName("monthly_income")
    private String monthlyIncome;
    @SerializedName("house_property")
    private String houseProperty;
    @SerializedName("credit_card_num")
    private int creditCardNum;
    @SerializedName("credit_card_quota")
    private String creditCardQuota;
    @SerializedName("loan_describe")
    private String loanDescribe;

    public String getJlaId() {
        return jlaId;
    }

    public void setJlaId(String jlaId) {
        this.jlaId = jlaId;
    }

    public String getMarriageMsg() {
        return marriageMsg;
    }

    public void setMarriageMsg(String marriageMsg) {
        this.marriageMsg = marriageMsg;
    }

    public String getEduMsg() {
        return eduMsg;
    }

    public void setEduMsg(String eduMsg) {
        this.eduMsg = eduMsg;
    }

    public String getWorkIndustry() {
        return workIndustry;
    }

    public void setWorkIndustry(String workIndustry) {
        this.workIndustry = workIndustry;
    }

    public String getWorkNumber() {
        return workNumber;
    }

    public void setWorkNumber(String workNumber) {
        this.workNumber = workNumber;
    }

    public String getWorkPost() {
        return workPost;
    }

    public void setWorkPost(String workPost) {
        this.workPost = workPost;
    }

    public String getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(String monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public String getHouseProperty() {
        return houseProperty;
    }

    public void setHouseProperty(String houseProperty) {
        this.houseProperty = houseProperty;
    }

    public int getCreditCardNum() {
        return creditCardNum;
    }

    public void setCreditCardNum(int creditCardNum) {
        this.creditCardNum = creditCardNum;
    }

    public String getCreditCardQuota() {
        return creditCardQuota;
    }

    public void setCreditCardQuota(String creditCardQuota) {
        this.creditCardQuota = creditCardQuota;
    }

    public String getLoanDescribe() {
        return loanDescribe;
    }

    public void setLoanDescribe(String loanDescribe) {
        this.loanDescribe = loanDescribe;
    }

    @Override
    protected String getBusiness() {
        return "CL038";
    }
}
