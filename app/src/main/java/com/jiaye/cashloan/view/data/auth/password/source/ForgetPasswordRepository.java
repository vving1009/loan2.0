package com.jiaye.cashloan.view.data.auth.password.source;

import android.database.Cursor;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.persistence.DbContract;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * ForgetPasswordRepository
 *
 * @author 贾博瑄
 */

public class ForgetPasswordRepository implements ForgetPasswordDataSource {

    @Override
    public Flowable<String> queryPhone() {
        return Flowable.just("SELECT phone FROM user;")
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String sql) throws Exception {
                        String phone = "";
                        Cursor cursor = LoanApplication.getInstance().getSQLiteDatabase().rawQuery(sql, null);
                        if (cursor != null) {
                            if (cursor.moveToNext()) {
                                phone = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_PHONE));
                            }
                            cursor.close();
                        }
                        return phone;
                    }
                });
    }
}
