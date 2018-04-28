package com.jiaye.cashloan.http.data.home;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildResponse;

import java.util.List;

/**
 * ProductList
 *
 * @author 贾博瑄
 */

public class ProductList extends SatcatcheChildResponse {

    @SerializedName("product_list")
    private List<Product> products;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
