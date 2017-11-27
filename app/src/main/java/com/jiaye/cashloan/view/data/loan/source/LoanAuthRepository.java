package com.jiaye.cashloan.view.data.loan.source;

import android.content.ContentValues;
import android.database.Cursor;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.data.loan.LoanAuth;
import com.jiaye.cashloan.http.data.loan.LoanAuthRequest;
import com.jiaye.cashloan.http.utils.ResponseTransformer;
import com.jiaye.cashloan.persistence.DbContract;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * LoanAuthRepository
 *
 * @author 贾博瑄
 */

public class LoanAuthRepository implements LoanAuthDataSource {

    @Override
    public Flowable<LoanAuth> requestLoanAuth() {
        return Flowable.just("")
                .map(new Function<String, LoanAuthRequest>() {
                    @Override
                    public LoanAuthRequest apply(String s) throws Exception {
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
                .compose(new ResponseTransformer<LoanAuthRequest, LoanAuth>("loanAuth"))
                .map(new Function<LoanAuth, LoanAuth>() {
                    @Override
                    public LoanAuth apply(LoanAuth loanAuth) throws Exception {
                        ContentValues values = new ContentValues();
                        values.put("loan_id", loanAuth.getLoanId());
                        LoanApplication.getInstance().getSQLiteDatabase().update("user", values, null, null);
                        return loanAuth;
                    }
                });
    }
}
