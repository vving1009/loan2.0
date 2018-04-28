package com.jiaye.cashloan.http.data.loan;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildResponse;

/**
 * LoanPlan
 *
 * @author 贾博瑄
 */

public class LoanPlan extends SatcatcheChildResponse {

    @SerializedName("repayment_plans")
    private Plan[] plans;

    public Plan[] getPlans() {
        return plans;
    }

    public void setPlans(Plan[] plans) {
        this.plans = plans;
    }

    public static class Plan {

        @SerializedName("return_date")
        private String date;

        @SerializedName("return_money")
        private String money;

        @SerializedName("return_status")
        private String status;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
