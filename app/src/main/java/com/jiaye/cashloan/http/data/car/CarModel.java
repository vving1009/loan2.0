package com.jiaye.cashloan.http.data.car;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CarModel {

    @SerializedName("data")
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * pyear : 2015  //2015款
         * chexing_list : [{"id":"20022441","cxname":"逸致 2015款 180E CVT跨界版","pyear":"2014","price":"16.28"}]
         */

        @SerializedName("pyear")
        private int pyear;
        @SerializedName("chexing_list")
        private List<ChexingListBean> chexingList;

        public int getPyear() {
            return pyear;
        }

        public void setPyear(int pyear) {
            this.pyear = pyear;
        }

        public List<ChexingListBean> getChexingList() {
            return chexingList;
        }

        public void setChexingList(List<ChexingListBean> chexingList) {
            this.chexingList = chexingList;
        }

        public static class ChexingListBean {
            /**
             * id : 20022441  //车型id
             * cxname : 逸致 2015款 180E CVT跨界版  //车型名称
             * pyear : 2014  //上市时间
             * price : 16.28  //发布价格
             */

            @SerializedName("id")
            private String id;
            @SerializedName("cxname")
            private String cxname;
            @SerializedName("pyear")
            private String pyear;
            @SerializedName("price")
            private String price;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getCxname() {
                return cxname;
            }

            public void setCxname(String cxname) {
                this.cxname = cxname;
            }

            public String getPyear() {
                return pyear;
            }

            public void setPyear(String pyear) {
                this.pyear = pyear;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }
        }
    }
}
