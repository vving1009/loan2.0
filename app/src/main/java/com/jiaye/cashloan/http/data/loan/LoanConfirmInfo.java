package com.jiaye.cashloan.http.data.loan;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.ChildResponse;

/**
 * LoanConfirmInfo
 *
 * @author 贾博瑄
 */

public class LoanConfirmInfo extends ChildResponse {

    /*借款编号*/
    @SerializedName("jla_id")
    private String loanId;

    /*借款金额*/
    @SerializedName("jk_money")
    private String loan;

    /*服务费*/
    @SerializedName("service_fee")
    private String service;

    /*咨询费*/
    @SerializedName("advisory_fee")
    private String consult;

    /*利息*/
    @SerializedName("interest")
    private String interest;

    /*借款期限*/
    @SerializedName("deadline")
    private String deadline;

    /*还款方式*/
    @SerializedName("refund_type")
    private String paymentMethod;

    /*还款金额*/
    @SerializedName("hk_money")
    private String amount;

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getLoan() {
        return loan;
    }

    public void setLoan(String loan) {
        this.loan = loan;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getConsult() {
        return consult;
    }

    public void setConsult(String consult) {
        this.consult = consult;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
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
}
