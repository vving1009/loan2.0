package com.jiaye.cashloan.view.data.home.source;

import com.jiaye.cashloan.http.data.home.Product;

import io.reactivex.Flowable;

/**
 * HomeDataSource
 *
 * @author 贾博瑄
 */

public interface HomeDataSource {

    /**
     * 增加产品
     */
    Flowable<Product> addProduct(Product product);
}
