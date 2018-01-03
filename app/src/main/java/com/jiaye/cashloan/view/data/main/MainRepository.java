package com.jiaye.cashloan.view.data.main;

import android.database.Cursor;
import android.text.TextUtils;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.my.User;
import com.jiaye.cashloan.persistence.DbContract;
import com.jiaye.cashloan.view.LocalException;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * Created by guozihua on 2018/1/3.
 */

public class MainRepository implements MainDataSource {

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
                        name = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_OCR_NAME));
                        phone = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_PHONE));
                    }
                    cursor.close();
                }
                if (TextUtils.isEmpty(phone)) {
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
