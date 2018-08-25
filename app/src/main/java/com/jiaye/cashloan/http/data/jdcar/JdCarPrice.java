package com.jiaye.cashloan.http.data.jdcar;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JdCarPrice {
    /**
     * basicInfo : {"brandname":"宝马","emission":"国4(国5)","familyname":"宝马5系新能源","localPrice":"暂无经销商报价","miles":"3","price":"69.86","province":"上海","regdate":"2016-01","salesdesc":"2015款 530Le"}
     * valuation : {"IndustryPurchasePrice":[24.76,33.45],"IndustryRetailPrice":[33.45,42.04],"PurchasePrice":29.58,"retailPrice":40.53}
     */

    @SerializedName("basicInfo")
    private BasicInfoBean basicInfo;
    @SerializedName("valuation")
    private ValuationBean valuation;

    public BasicInfoBean getBasicInfo() {
        return basicInfo;
    }

    public void setBasicInfo(BasicInfoBean basicInfo) {
        this.basicInfo = basicInfo;
    }

    public ValuationBean getValuation() {
        return valuation;
    }

    public void setValuation(ValuationBean valuation) {
        this.valuation = valuation;
    }

    public static class BasicInfoBean {
        /**
         * brandname : 宝马
         * emission : 国4(国5)
         * familyname : 宝马5系新能源
         * localPrice : 暂无经销商报价
         * miles : 3
         * price : 69.86
         * province : 上海
         * regdate : 2016-01
         * salesdesc : 2015款 530Le
         */

        @SerializedName("brandname")
        private String brandname;
        @SerializedName("emission")
        private String emission;
        @SerializedName("familyname")
        private String familyname;
        @SerializedName("localPrice")
        private String localPrice;
        @SerializedName("miles")
        private String miles;
        @SerializedName("price")
        private String price;
        @SerializedName("province")
        private String province;
        @SerializedName("regdate")
        private String regdate;
        @SerializedName("salesdesc")
        private String salesdesc;

        public String getBrandname() {
            return brandname;
        }

        public void setBrandname(String brandname) {
            this.brandname = brandname;
        }

        public String getEmission() {
            return emission;
        }

        public void setEmission(String emission) {
            this.emission = emission;
        }

        public String getFamilyname() {
            return familyname;
        }

        public void setFamilyname(String familyname) {
            this.familyname = familyname;
        }

        public String getLocalPrice() {
            return localPrice;
        }

        public void setLocalPrice(String localPrice) {
            this.localPrice = localPrice;
        }

        public String getMiles() {
            return miles;
        }

        public void setMiles(String miles) {
            this.miles = miles;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getRegdate() {
            return regdate;
        }

        public void setRegdate(String regdate) {
            this.regdate = regdate;
        }

        public String getSalesdesc() {
            return salesdesc;
        }

        public void setSalesdesc(String salesdesc) {
            this.salesdesc = salesdesc;
        }
    }

    public static class ValuationBean {
        /**
         * IndustryPurchasePrice : [24.76,33.45]
         * IndustryRetailPrice : [33.45,42.04]
         * PurchasePrice : 29.58
         * retailPrice : 40.53
         */

        @SerializedName("PurchasePrice")
        private double purchasePrice;
        @SerializedName("retailPrice")
        private double retailPrice;
        @SerializedName("IndustryPurchasePrice")
        private List<Double> industryPurchasePrice;
        @SerializedName("IndustryRetailPrice")
        private List<Double> industryRetailPrice;

        public double getPurchasePrice() {
            return purchasePrice;
        }

        public void setPurchasePrice(double purchasePrice) {
            this.purchasePrice = purchasePrice;
        }

        public double getRetailPrice() {
            return retailPrice;
        }

        public void setRetailPrice(double retailPrice) {
            this.retailPrice = retailPrice;
        }

        public List<Double> getIndustryPurchasePrice() {
            return industryPurchasePrice;
        }

        public void setIndustryPurchasePrice(List<Double> industryPurchasePrice) {
            this.industryPurchasePrice = industryPurchasePrice;
        }

        public List<Double> getIndustryRetailPrice() {
            return industryRetailPrice;
        }

        public void setIndustryRetailPrice(List<Double> industryRetailPrice) {
            this.industryRetailPrice = industryRetailPrice;
        }
    }
}
