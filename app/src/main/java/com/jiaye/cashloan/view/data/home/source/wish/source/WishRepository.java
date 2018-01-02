package com.jiaye.cashloan.view.data.home.source.wish.source;

import com.jiaye.cashloan.http.data.home.Product;

import io.reactivex.Flowable;

/**
 * Created by guozihua on 2018/1/2.
 */

public class WishRepository implements WishDataSource{
    @Override
    public Flowable<Product> commit() {
        return null;
    }
}
