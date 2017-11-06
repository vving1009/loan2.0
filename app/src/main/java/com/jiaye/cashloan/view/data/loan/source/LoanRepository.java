package com.jiaye.cashloan.view.data.loan.source;

import android.database.Cursor;
import android.text.TextUtils;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.home.Product;
import com.jiaye.cashloan.http.data.loan.LoanAuth;
import com.jiaye.cashloan.http.data.loan.LoanAuthRequest;
import com.jiaye.cashloan.http.utils.ResponseTransformer;
import com.jiaye.cashloan.persistence.DbContract;
import com.jiaye.cashloan.view.LocalException;
import com.jiaye.cashloan.view.data.auth.User;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * LoanRepository
 *
 * @author 贾博瑄
 */

public class LoanRepository implements LoanDataSource {

    @Override
    public Flowable<Product> queryProduct() {
        return Flowable.just("select * from product").map(new Function<String, Product>() {
            @Override
            public Product apply(String sql) throws Exception {
                Product product = null;
                Cursor cursor = LoanApplication.getInstance().getSQLiteDatabase().rawQuery(sql, null);
                if (cursor != null) {
                    if (cursor.moveToNext()) {
                        String productId = cursor.getString(cursor.getColumnIndex(DbContract.Product.COLUMN_NAME_PRODUCT_ID));
                        int amount = cursor.getInt(cursor.getColumnIndex(DbContract.Product.COLUMN_NAME_AMOUNT));
                        int deadline = cursor.getInt(cursor.getColumnIndex(DbContract.Product.COLUMN_NAME_DEADLINE));
                        String paymentMethod = cursor.getString(cursor.getColumnIndex(DbContract.Product.COLUMN_NAME_PAYMENT_METHOD));
                        product = new Product();
                        product.setId(productId);
                        product.setAmount(amount);
                        product.setDeadline(deadline);
                        product.setPaymentMethod(paymentMethod);
                    }
                    cursor.close();
                }
                return product;
            }
        }).filter(new Predicate<Product>() {
            @Override
            public boolean test(Product product) throws Exception {
                return product != null;
            }
        });
    }

    @Override
    public Flowable<Product> requestProduct() {
        // TODO: 2017/11/6 服务器请求,并保存产品信息
        return Flowable.empty();
    }

    @Override
    public Flowable<User> queryUser() {
        return Flowable.just("SELECT * FROM user").map(new Function<String, User>() {
            @Override
            public User apply(String sql) throws Exception {
                String token = "";
                String name = "";
                String phone = "";
                Cursor cursor = LoanApplication.getInstance().getSQLiteDatabase().rawQuery(sql, null);
                if (cursor != null) {
                    if (cursor.moveToNext()) {
                        token = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_TOKEN));
                        name = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_NAME));
                        phone = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_PHONE));
                    }
                    cursor.close();
                }
                if (TextUtils.isEmpty(token)) {
                    throw new LocalException(R.string.error_auth_not_log_in);
                } else {
                    User user = new User();
                    user.setToken(token);
                    user.setName(name);
                    user.setPhone(phone);
                    return user;
                }
            }
        });
    }
}
