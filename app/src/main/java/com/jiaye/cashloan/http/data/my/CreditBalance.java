package com.jiaye.cashloan.http.data.my;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.ChildResponse;

/**
 * CreditBalance
 *
 * @author 贾博瑄
 */

public class CreditBalance extends ChildResponse {

    @SerializedName("result")
    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public class Result {

        @SerializedName("reponseStr")
        private Content content;

        public Content getContent() {
            return content;
        }

        public void setContent(Content content) {
            this.content = content;
        }
    }

    public class Content {

        /*可用余额*/
        @SerializedName("availBal")
        private String availBal;

        /*账面余额*/
        @SerializedName("currBal")
        private String currBal;

        public String getAvailBal() {
            return availBal;
        }

        public void setAvailBal(String availBal) {
            this.availBal = availBal;
        }

        public String getCurrBal() {
            return currBal;
        }

        public void setCurrBal(String currBal) {
            this.currBal = currBal;
        }
    }
}
