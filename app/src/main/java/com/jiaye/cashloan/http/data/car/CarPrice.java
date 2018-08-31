package com.jiaye.cashloan.http.data.car;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CarPrice {

    @SerializedName("est_price_result")
    private List<Double> estPriceResult;
    @SerializedName("est_price_area")
    private List<EstPriceAreaBean> estPriceArea;

    public List<Double> getEstPriceResult() {
        return estPriceResult;
    }

    public void setEstPriceResult(List<Double> estPriceResult) {
        this.estPriceResult = estPriceResult;
    }

    public List<EstPriceAreaBean> getEstPriceArea() {
        return estPriceArea;
    }

    public void setEstPriceArea(List<EstPriceAreaBean> estPriceArea) {
        this.estPriceArea = estPriceArea;
    }

    public static class EstPriceAreaBean {
        /**
         * price : 14.08
         * area : 东北地区
         * areaid : 1
         */

        @SerializedName("price")
        private double price;
        @SerializedName("area")
        private String area;
        @SerializedName("areaid")
        private String areaid;

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getAreaid() {
            return areaid;
        }

        public void setAreaid(String areaid) {
            this.areaid = areaid;
        }
    }
}
