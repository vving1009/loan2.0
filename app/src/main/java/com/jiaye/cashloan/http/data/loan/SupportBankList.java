package com.jiaye.cashloan.http.data.loan;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.ChildResponse;

import java.util.List;

/**
 * SupportBankList
 *
 * @author 贾博瑄
 */

public class SupportBankList extends ChildResponse {

    @SerializedName("bank_data")
    private List<Data> list;

    public List<Data> getList() {
        return list;
    }

    public void setList(List<Data> list) {
        this.list = list;
    }

    public class Data {

        @SerializedName("bk_id")
        private String id;

        @SerializedName("bk_name")
        private String name;

        @SerializedName("bk_remark")
        private String remark;

        @SerializedName("bk_db")
        private String onceLimit;

        @SerializedName("bk_dr")
        private String dayLimit;

        @SerializedName("bk_dy")
        private String monthLimit;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getOnceLimit() {
            return onceLimit;
        }

        public void setOnceLimit(String onceLimit) {
            this.onceLimit = onceLimit;
        }

        public String getDayLimit() {
            return dayLimit;
        }

        public void setDayLimit(String dayLimit) {
            this.dayLimit = dayLimit;
        }

        public String getMonthLimit() {
            return monthLimit;
        }

        public void setMonthLimit(String monthLimit) {
            this.monthLimit = monthLimit;
        }
    }
}
