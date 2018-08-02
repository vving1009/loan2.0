package com.jiaye.cashloan.http.data.ststoken;

import com.google.gson.annotations.SerializedName;

/**
 * StsTokenResponse
 *
 * @author 贾博瑄
 */
public class StsTokenResponse {

    @SerializedName("returnCode")
    private String returnCode;

    @SerializedName("returnMsg")
    private String returnMsg;

    @SerializedName("jyrcResData")
    private JyrcResDataBean jyrcResData;

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public JyrcResDataBean getJyrcResData() {
        return jyrcResData;
    }

    public void setJyrcResData(JyrcResDataBean jyrcResData) {
        this.jyrcResData = jyrcResData;
    }

    public static class JyrcResDataBean {

        @SerializedName("accessKeyId")
        private String accessKeyId;

        @SerializedName("accessKeySecret")
        private String accessKeySecret;

        @SerializedName("securityToken")
        private String securityToken;

        @SerializedName("time")
        private String expiration;

        public String getAccessKeyId() {
            return accessKeyId;
        }

        public void setAccessKeyId(String accessKeyId) {
            this.accessKeyId = accessKeyId;
        }

        public String getAccessKeySecret() {
            return accessKeySecret;
        }

        public void setAccessKeySecret(String accessKeySecret) {
            this.accessKeySecret = accessKeySecret;
        }

        public String getSecurityToken() {
            return securityToken;
        }

        public void setSecurityToken(String securityToken) {
            this.securityToken = securityToken;
        }

        public String getExpiration() {
            return expiration;
        }

        public void setExpiration(String expiration) {
            this.expiration = expiration;
        }
    }
}
