package com.jiaye.cashloan.view.data.market;

import android.database.Cursor;
import android.text.TextUtils;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.persistence.DbContract;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * Created by guozihua on 2018/1/4.
 */

public class MarketRepository implements MarketDataSource {


    public Flowable<Boolean> checkLogin() {
        return Flowable.just("SELECT * FROM user").map(new Function<String, Boolean>() {
            @Override
            public Boolean apply(String sql) throws Exception {
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
                   return false ;
                } else {
                    return true ;
                }
            }
        });
    }

}
