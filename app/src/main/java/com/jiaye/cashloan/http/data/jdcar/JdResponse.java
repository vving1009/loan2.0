package com.jiaye.cashloan.http.data.jdcar;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JdResponse<T> {

    /**
     * code : 10000
     * charge : false
     * msg : 查询成功
     * result :
     */

    @SerializedName("code")
    private String code;
    @SerializedName("charge")
    private boolean charge;
    @SerializedName("msg")
    private String msg;
    @SerializedName("result")
    private ResultBean<T> result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isCharge() {
        return charge;
    }

    public void setCharge(boolean charge) {
        this.charge = charge;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResultBean<T> getResult() {
        return result;
    }

    public void setResult(ResultBean<T> result) {
        this.result = result;
    }

    public static class ResultBean<T> {
        /**
         * code : 1000
         * message : 调用成功
         * result :
         */

        @SerializedName("code")
        private String code;
        @SerializedName("message")
        private String message;
        @SerializedName("result")
        private ResultBeanInner<T> resultInner;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public ResultBeanInner<T> getResultInner() {
            return resultInner;
        }

        public void setResultInner(ResultBeanInner<T> result) {
            this.resultInner = result;
        }

        public static class ResultBeanInner<T> {
            /**
             * code : 200
             * message : success
             * result :
             */

            @SerializedName("code")
            private String code;
            @SerializedName("message")
            private String message;
            @SerializedName("result")
            private List<T> result;
            @SerializedName("body")
            private T body;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public List<T> getResult() {
                return result;
            }

            public void setResult(List<T> result) {
                this.result = result;
            }

            public T getBody() {
                return body;
            }

            public void setBody(T body) {
                this.body = body;
            }
        }
    }
}
