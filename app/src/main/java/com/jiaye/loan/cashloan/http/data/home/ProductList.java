package com.jiaye.loan.cashloan.http.data.home;

import com.google.gson.annotations.SerializedName;
import com.jiaye.loan.cashloan.http.base.ChildResponse;

import java.util.List;

/**
 * ProductList
 *
 * @author 贾博瑄
 */

public class ProductList extends ChildResponse {

    @SerializedName("product_list")
    private List<Product> products;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
