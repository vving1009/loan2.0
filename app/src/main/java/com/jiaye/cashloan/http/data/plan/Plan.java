package com.jiaye.cashloan.http.data.plan;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildResponse;

import java.util.List;

/**
 * Plan
 *
 * @author 贾博瑄
 */
public class Plan extends SatcatcheChildResponse {

    @SerializedName("repaymentDetails")
    private List<Details> list;

    public List<Details> getList() {
        return list;
    }

    public void setList(List<Details> list) {
        this.list = list;
    }

    public static class Details {

        private String period;

        /**
         * 还款日期
         */
        @SerializedName("repaymentDate")
        private String date;

        /**
         * 还款金额
         */
        @SerializedName("repaymentAmount")
        private String repayment;

        /**
         * 本金
         */
        @SerializedName("currentMonthPrincipal")
        private String principal;

        /**
         * 优先显示利息为空时显示这个利息
         */
        @SerializedName("currentMonthInterest")
        private String interest;

        /**
         * 服务费
         */
        @SerializedName("actualServiceCharge")
        private String charge;

        private String advanceRepaymentAmount;

        private String currentMonthRecoverPrincipal;

        /**
         * 优先显示利息
         */
        @SerializedName("financialCustomerInterest")
        private String financialCustomerInterest;

        private String gpsflowfee;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getRepayment() {
            return repayment;
        }

        public void setRepayment(String repayment) {
            this.repayment = repayment;
        }

        public String getPrincipal() {
            return principal;
        }

        public void setPrincipal(String principal) {
            this.principal = principal;
        }

        public String getInterest() {
            return interest;
        }

        public void setInterest(String interest) {
            this.interest = interest;
        }

        public String getCharge() {
            return charge;
        }

        public void setCharge(String charge) {
            this.charge = charge;
        }

        public String getCurrentMonthRecoverPrincipal() {
            return currentMonthRecoverPrincipal;
        }

        public void setCurrentMonthRecoverPrincipal(String currentMonthRecoverPrincipal) {
            this.currentMonthRecoverPrincipal = currentMonthRecoverPrincipal;
        }

        public String getFinancialCustomerInterest() {
            return financialCustomerInterest;
        }

        public void setFinancialCustomerInterest(String financialCustomerInterest) {
            this.financialCustomerInterest = financialCustomerInterest;
        }
    }
}
