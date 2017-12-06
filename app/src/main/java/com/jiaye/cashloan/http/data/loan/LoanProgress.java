package com.jiaye.cashloan.http.data.loan;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.ChildResponse;

import java.util.List;

/**
 * LoanProgress
 *
 * @author 贾博瑄
 */

public class LoanProgress extends ChildResponse {

    @SerializedName("loan_data")
    private List<Data> mList;

    public List<Data> getList() {
        return mList;
    }

    public void setList(List<Data> list) {
        mList = list;
    }

    public static class Data {

        /*
        01未提交;
        02提交成功;
        03审核中;
        04审核通过;
        05审核未通过;
        06额度已失效;
        07未绑定银行卡;
        08绑卡失败;
        09绑卡成功;
        10未签约;
        11已签约;
        12待放款;
        13已放款;
        14未还款;
        15还款中;
        16结清*/
        @SerializedName("loan_status")
        private String status;

        @SerializedName("loan_statusMsg")
        private String msg;

        @SerializedName("loan_time")
        private String time;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
