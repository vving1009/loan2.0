package com.jiaye.cashloan.view.data.home;

import android.database.Cursor;
import android.text.TextUtils;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.home.BannerList;
import com.jiaye.cashloan.http.data.home.BannerListRequest;
import com.jiaye.cashloan.http.data.home.ProductList;
import com.jiaye.cashloan.http.data.home.ProductListRequest;
import com.jiaye.cashloan.http.data.loan.CheckLoan;
import com.jiaye.cashloan.http.data.loan.CheckLoanRequest;
import com.jiaye.cashloan.http.data.my.User;
import com.jiaye.cashloan.http.utils.SatcatcheResponseTransformer;
import com.jiaye.cashloan.persistence.DbContract;
import com.jiaye.cashloan.view.LocalException;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * HomeRepository
 *
 * @author 贾博瑄
 */

public class HomeRepository implements HomeDataSource {

    @Override
    public Flowable<BannerList.Banner[]> requestBannerList() {
        return Flowable.just(new BannerListRequest())
                .compose(new SatcatcheResponseTransformer<BannerListRequest, BannerList>
                        ("bannerList"))
                .map(new Function<BannerList, BannerList.Banner[]>() {
                    @Override
                    public BannerList.Banner[] apply(BannerList bannerList) throws Exception {
                        return bannerList.getBannerArray();
                    }
                });
    }

    @Override
    public Flowable<ProductList.Product[]> requestProductList() {
        return Flowable.just(new ProductListRequest())
                .compose(new SatcatcheResponseTransformer<ProductListRequest, ProductList>
                        ("productList"))
                .map(new Function<ProductList, ProductList.Product[]>() {
                    @Override
                    public ProductList.Product[] apply(ProductList productList) throws Exception {
                        return productList.getProductArray();
                    }
                });
    }

    @Override
    public Flowable<CheckLoan> requestCheckLoan() {
        return queryUser()
                .map(new Function<User, CheckLoanRequest>() {
                    @Override
                    public CheckLoanRequest apply(User user) throws Exception {
                        return new CheckLoanRequest();
                    }
                })
                .compose(new SatcatcheResponseTransformer<CheckLoanRequest, CheckLoan>("checkLoan"));
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
}
