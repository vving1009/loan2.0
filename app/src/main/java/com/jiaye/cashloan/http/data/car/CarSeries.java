package com.jiaye.cashloan.http.data.car;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CarSeries {

    @SerializedName("pinpai_list")
    private List<PinpaiListBean> pinpaiList;

    public List<PinpaiListBean> getPinpaiList() {
        return pinpaiList;
    }

    public void setPinpaiList(List<PinpaiListBean> pinpaiList) {
        this.pinpaiList = pinpaiList;
    }

    public static class PinpaiListBean {
        /**
         * ppid : 2000033
         * ppname : 广州丰田
         * xilie : [{"xlid":"20001414","xlname":"雅力士"},{"xlid":"20001603","xlname":"逸致"}]
         */

        @SerializedName("ppid")
        private String ppid;
        @SerializedName("ppname")
        private String ppname;
        @SerializedName("xilie")
        private List<XilieBean> xilie;

        public String getPpid() {
            return ppid;
        }

        public void setPpid(String ppid) {
            this.ppid = ppid;
        }

        public String getPpname() {
            return ppname;
        }

        public void setPpname(String ppname) {
            this.ppname = ppname;
        }

        public List<XilieBean> getXilie() {
            return xilie;
        }

        public void setXilie(List<XilieBean> xilie) {
            this.xilie = xilie;
        }

        public static class XilieBean {
            /**
             * xlid : 20001414
             * xlname : 雅力士
             */

            @SerializedName("xlid")
            private String xlid;
            @SerializedName("xlname")
            private String xlname;

            public String getXlid() {
                return xlid;
            }

            public void setXlid(String xlid) {
                this.xlid = xlid;
            }

            public String getXlname() {
                return xlname;
            }

            public void setXlname(String xlname) {
                this.xlname = xlname;
            }
        }
    }
}
