package com.jiaye.cashloan.http.data.home;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildResponse;

/**
 * ProductList
 *
 * @author 贾博瑄
 */
public class ProductList extends SatcatcheChildResponse {

    @SerializedName("product_data")
    private Product[] productArray;

    public Product[] getProductArray() {
        return productArray;
    }

    public void setProductArray(Product[] productArray) {
        this.productArray = productArray;
    }

    public class Product {

        /**
         * 产品编号
         */
        @SerializedName("jpd_id")
        private String id;

        /**
         * 产品类别
         */
        @SerializedName("jpd_type")
        private String type;

        /**
         * 图片地址
         */
        @SerializedName("picture_url")
        private String url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
