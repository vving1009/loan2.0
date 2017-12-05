package com.jiaye.cashloan.view.data.home.source;

import android.content.ContentValues;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.data.home.Product;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * HomeRepository
 *
 * @author 贾博瑄
 */

public class HomeRepository implements HomeDataSource {

    @Override
    public Flowable<Product> addProduct(Product product) {
        return Flowable.just(product).map(new Function<Product, Product>() {
            @Override
            public Product apply(Product product) throws Exception {
                ContentValues values = new ContentValues();
                values.put("product_id", product.getId());
                values.put("product_name", product.getName());
                values.put("amount", product.getAmount());
                values.put("deadline", product.getDeadline());
                values.put("payment_method", product.getPaymentMethod());
                values.put("is_default", 0);
                LoanApplication.getInstance().getSQLiteDatabase().delete("product", null, null);
                LoanApplication.getInstance().getSQLiteDatabase().insert("product", null, values);
                return product;
            }
        });
    }
}
