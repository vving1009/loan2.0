package com.jiaye.cashloan.http.data.loan;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildResponse;

/**
 * LoanApply
 *
 * @author 贾博瑄
 */

public class LoanApply extends SatcatcheChildResponse {

    /*产品信息*/
    @SerializedName("card_array")
    private Card[] cards;

    public Card[] getCards() {
        return cards;
    }

    public void setCards(Card[] cards) {
        this.cards = cards;
    }

    public static class Card {

        /*申请编号*/
        @SerializedName("jla_id")
        private String id;

        /*日期*/
        @SerializedName("jla_enterDate")
        private String date;

        /*产品名称*/
        @SerializedName("jpd_name")
        private String name;

        /*审批金额*/
        @SerializedName("approve_sum")
        private String approveNum;

        /*总还款额*/
        @SerializedName("compact_sum")
        private String compactNum;

        /*借款金额*/
        @SerializedName("jk_sum")
        private String loanNum;

        /*综合费率*/
        @SerializedName("fee_rate")
        private String fee;

        /*本期还款日*/
        @SerializedName("return_date")
        private String returnDate;

        /*还款状态*/
        @SerializedName("return_status")
        private String returnState;

        /*是否查看还款计划 0 否 1 是*/
        @SerializedName("if_replayment")
        private String plan;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getApproveNum() {
            return approveNum;
        }

        public void setApproveNum(String approveNum) {
            this.approveNum = approveNum;
        }

        public String getCompactNum() {
            return compactNum;
        }

        public void setCompactNum(String compactNum) {
            this.compactNum = compactNum;
        }

        public String getLoanNum() {
            return loanNum;
        }

        public void setLoanNum(String loanNum) {
            this.loanNum = loanNum;
        }

        public String getFee() {
            return fee;
        }

        public void setFee(String fee) {
            this.fee = fee;
        }

        public String getReturnDate() {
            return returnDate;
        }

        public void setReturnDate(String returnDate) {
            this.returnDate = returnDate;
        }

        public String getReturnState() {
            return returnState;
        }

        public void setReturnState(String returnState) {
            this.returnState = returnState;
        }

        public String getPlan() {
            return plan;
        }

        public void setPlan(String plan) {
            this.plan = plan;
        }
    }
}
