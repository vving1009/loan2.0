package com.jiaye.cashloan.http.data.ststoken;

import com.google.gson.annotations.SerializedName;

/**
 * StsTokenRequest
 *
 * @author 贾博瑄
 */
public class StsTokenRequest {

    @SerializedName("jyrcPubData")
    private JyrcPubDataBean jyrcPubData = new JyrcPubDataBean();

    public JyrcPubDataBean getJyrcPubData() {
        return jyrcPubData;
    }

    public void setJyrcPubData(JyrcPubDataBean jyrcPubData) {
        this.jyrcPubData = jyrcPubData;
    }

    public static class JyrcPubDataBean {

        @SerializedName("reqNo")
        private String reqNo = "reqNo";

        @SerializedName("userinfoId")
        private String userinfoId = "userinfoId";

        @SerializedName("cId")
        private String cId = "cId";

        @SerializedName("cIp")
        private String cIp = "cIp";

        @SerializedName("cName")
        private String cName = "cName";

        public String getReqNo() {
            return reqNo;
        }

        public void setReqNo(String reqNo) {
            this.reqNo = reqNo;
        }

        public String getUserinfoId() {
            return userinfoId;
        }

        public void setUserinfoId(String userinfoId) {
            this.userinfoId = userinfoId;
        }

        public String getCId() {
            return cId;
        }

        public void setCId(String cId) {
            this.cId = cId;
        }

        public String getCIp() {
            return cIp;
        }

        public void setCIp(String cIp) {
            this.cIp = cIp;
        }

        public String getCName() {
            return cName;
        }

        public void setCName(String cName) {
            this.cName = cName;
        }
    }
}
