package com.jiaye.cashloan.view.data.home.source.wish.source;

import com.jiaye.cashloan.http.data.home.Product;

import io.reactivex.Flowable;

/**
 * Created by guozihua on 2018/1/2.
 */

public interface WishDataSource {
    /**
     * 提交申请
     */
    Flowable<Product> commit();
}
