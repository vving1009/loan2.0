package com.jiaye.cashloan.view.data.loan.source;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.loan.CheckLoan;
import com.jiaye.cashloan.http.data.loan.CheckLoanRequest;
import com.jiaye.cashloan.http.data.loan.DefaultProduct;
import com.jiaye.cashloan.http.data.loan.DefaultProductRequest;
import com.jiaye.cashloan.http.data.loan.LoanAuth;
import com.jiaye.cashloan.http.data.loan.LoanAuthRequest;
import com.jiaye.cashloan.http.data.my.User;
import com.jiaye.cashloan.http.utils.ResponseTransformer;
import com.jiaye.cashloan.persistence.DbContract;
import com.jiaye.cashloan.view.LocalException;

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
    public Flowable<DefaultProduct> queryProduct() {
        return Flowable.just("select * from product where is_default = 0").map(new Function<String, DefaultProduct>() {
            @Override
            public DefaultProduct apply(String sql) throws Exception {
                return queryProduct(sql);
            }
        }).filter(new Predicate<DefaultProduct>() {
            @Override
            public boolean test(DefaultProduct product) throws Exception {
                return product != null;
            }
        });
    }

    @Override
    public Flowable<DefaultProduct> queryDefaultProduct() {
        return Flowable.just("select * from product where is_default = 1").map(new Function<String, DefaultProduct>() {
            @Override
            public DefaultProduct apply(String sql) throws Exception {
                return queryProduct(sql);
            }
        }).filter(new Predicate<DefaultProduct>() {
            @Override
            public boolean test(DefaultProduct product) throws Exception {
                return product.getId() != null;
            }
        });
    }

    @Override
    public Flowable<DefaultProduct> requestProduct() {
        return Flowable.just(new DefaultProductRequest())
                .compose(new ResponseTransformer<DefaultProductRequest, DefaultProduct>("defaultProduct"))
                .map(new Function<DefaultProduct, DefaultProduct>() {
                    @Override
                    public DefaultProduct apply(DefaultProduct defaultProduct) throws Exception {
                        ContentValues values = new ContentValues();
                        values.put("product_id", defaultProduct.getId());
                        values.put("product_name", defaultProduct.getName());
                        String amount = defaultProduct.getAmount();
                        values.put("amount", amount.substring(0, amount.indexOf(".")));
                        values.put("deadline", defaultProduct.getDeadline());
                        values.put("is_default", 1);
                        LoanApplication.getInstance().getSQLiteDatabase().delete("product", null, null);
                        LoanApplication.getInstance().getSQLiteDatabase().insert("product", null, values);
                        return defaultProduct;
                    }
                });
    }

    @Override
    public Flowable<LoanAuth> requestCheck() {
        return queryUser()
                .map(new Function<User, CheckLoanRequest>() {
                    @Override
                    public CheckLoanRequest apply(User user) throws Exception {
                        return new CheckLoanRequest();
                    }
                }).compose(new ResponseTransformer<CheckLoanRequest, CheckLoan>("checkLoan"))
                .map(new Function<CheckLoan, CheckLoan>() {
                    @Override
                    public CheckLoan apply(CheckLoan checkLoan) throws Exception {
                        if (checkLoan.getCheck().equals("1")) {
                            return checkLoan;
                        }
                        throw new LocalException(R.string.error_loan);
                    }
                })
                .map(new Function<CheckLoan, LoanAuthRequest>() {
                    @Override
                    public LoanAuthRequest apply(CheckLoan s) throws Exception {
                        LoanAuthRequest request = new LoanAuthRequest();
                        Cursor cursorUser = LoanApplication.getInstance().getSQLiteDatabase().rawQuery("SELECT phone FROM user", null);
                        if (cursorUser != null) {
                            if (cursorUser.moveToNext()) {
                                String phone = cursorUser.getString(cursorUser.getColumnIndex(DbContract.User.COLUMN_NAME_PHONE));
                                request.setPhone(phone);
                            }
                            cursorUser.close();
                        }
                        Cursor cursorProduct = LoanApplication.getInstance().getSQLiteDatabase().rawQuery("SELECT product_id FROM product;", null);
                        if (cursorProduct != null) {
                            if (cursorProduct.moveToNext()) {
                                String productId = cursorProduct.getString(cursorProduct.getColumnIndex(DbContract.Product.COLUMN_NAME_PRODUCT_ID));
                                request.setProductId(productId);
                            }
                            cursorProduct.close();
                        }
                        return request;
                    }
                })
                .compose(new ResponseTransformer<LoanAuthRequest, LoanAuth>("loanAuth"));
    }

    private Flowable<User> queryUser() {
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
                        name = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_OCR_NAME));
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

    private DefaultProduct queryProduct(String sql) {
        DefaultProduct product = new DefaultProduct();
        Cursor cursor = LoanApplication.getInstance().getSQLiteDatabase().rawQuery(sql, null);
        if (cursor != null) {
            if (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex(DbContract.Product.COLUMN_NAME_PRODUCT_ID));
                String name = cursor.getString(cursor.getColumnIndex(DbContract.Product.COLUMN_NAME_PRODUCT_NAME));
                String amount = cursor.getString(cursor.getColumnIndex(DbContract.Product.COLUMN_NAME_AMOUNT));
                String deadline = cursor.getString(cursor.getColumnIndex(DbContract.Product.COLUMN_NAME_DEADLINE));
                product.setId(id);
                product.setName(name);
                product.setAmount(amount + ".00");
                product.setDeadline(deadline);
            }
            cursor.close();
        }
        return product;
    }
}
