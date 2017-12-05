package com.jiaye.cashloan.http.data.loan;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.ChildResponse;

/**
 * LoanDetails
 *
 * @author 贾博瑄
 */

public class LoanDetails extends ChildResponse {

    @SerializedName("jla_id")
    private String loanId;

    @SerializedName("jpd_name")
    private String name;

    @SerializedName("jla_enterdate")
    private String date;

    @SerializedName("jla_approversum")
    private String loan;

    @SerializedName("jpd_feerate")
    private String other;

    @SerializedName("jpd_refundtype")
    private String paymentMethod;

    @SerializedName("jla_contractsum")
    private String amount;

    @SerializedName("jrp_returnMoney")
    private String currentAmount;

    @SerializedName("jrp_returndate")
    private String deadline;

    @SerializedName("jca_satus")
    private String status;

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLoan() {
        return loan;
    }

    public void setLoan(String loan) {
        this.loan = loan;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(String currentAmount) {
        this.currentAmount = currentAmount;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
