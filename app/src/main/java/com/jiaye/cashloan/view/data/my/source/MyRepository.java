package com.jiaye.cashloan.view.data.my.source;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.auth.Auth;
import com.jiaye.cashloan.http.data.auth.AuthRequest;
import com.jiaye.cashloan.http.data.my.User;
import com.jiaye.cashloan.http.data.my.UserRequest;
import com.jiaye.cashloan.http.utils.SatcatcheResponseTransformer;
import com.jiaye.cashloan.persistence.DbContract;
import com.jiaye.cashloan.view.LocalException;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * MyRepository
 *
 * @author 贾博瑄
 */

public class MyRepository implements MyDataSource {

    @Override
    public Flowable<User> requestUser() {
        return Flowable.just("SELECT token FROM user;")
                .flatMap(new Function<String, Publisher<User>>() {
                    @Override
                    public Publisher<User> apply(String sql) throws Exception {
                        String token = "";
                        Cursor cursor = LoanApplication.getInstance().getSQLiteDatabase().rawQuery(sql, null);
                        if (cursor != null) {
                            if (cursor.moveToNext()) {
                                token = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_TOKEN));
                            }
                            cursor.close();
                        }
                        if (TextUtils.isEmpty(token)) {
                            return emptyUser();
                        } else {
                            return remoteUser();
                        }
                    }
                });
    }

    @Override
    public Flowable<User> remoteUser() {
        return Flowable.just(new UserRequest())
                .compose(new SatcatcheResponseTransformer<UserRequest, User>("user"))
                .map(new Function<User, User>() {
                    @Override
                    public User apply(User user) throws Exception {
                        /*更新数据库*/
                        ContentValues values = new ContentValues();
                        values.put("approve_number", user.getApproveNumber());
                        values.put("progress_number", user.getProgressNumber());
                        values.put("history_number", user.getHistoryNumber());
                        values.put("ocr_name", user.getName());
                        values.put("ocr_id", user.getId());
                        LoanApplication.getInstance().getSQLiteDatabase().update("user", values, null, null);
                        /*查询手机号*/
                        String phone = "";
                        Cursor cursor = LoanApplication.getInstance().getSQLiteDatabase().rawQuery("SELECT * FROM user;", null);
                        if (cursor != null) {
                            if (cursor.moveToNext()) {
                                phone = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_PHONE));
                            }
                            cursor.close();
                        }
                        user.setPhone(phone);
                        return user;
                    }
                });
    }

    @Override
    public Flowable<Auth> requestAuth() {
        return queryUser().flatMap(new Function<User, Publisher<Auth>>() {
            @Override
            public Publisher<Auth> apply(User user) throws Exception {
                return Flowable.just(new AuthRequest())
                        .compose(new SatcatcheResponseTransformer<AuthRequest, Auth>
                                ("auth"));
            }
        });
    }

    @Override
    public Flowable<User> queryUser() {
        String sql = "SELECT * FROM user;";
        return Flowable.just(sql).map(new Function<String, User>() {
            @Override
            public User apply(String sql) throws Exception {
                String phone = "";
                int approveNumber = 0;
                int progressNumber = 0;
                int historyNumber = 0;
                Cursor cursor = LoanApplication.getInstance().getSQLiteDatabase().rawQuery(sql, null);
                if (cursor != null) {
                    if (cursor.moveToNext()) {
                        phone = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_PHONE));
                        approveNumber = cursor.getInt(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_APPROVE_NUMBER));
                        progressNumber = cursor.getInt(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_PROGRESS_NUMBER));
                        historyNumber = cursor.getInt(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_HISTORY_NUMBER));
                    }
                    cursor.close();
                }
                if (TextUtils.isEmpty(phone)) {
                    throw new LocalException(R.string.error_auth_not_log_in);
                }
                User user = new User();
                user.setPhone(phone);
                user.setApproveNumber(approveNumber);
                user.setProgressNumber(progressNumber);
                user.setHistoryNumber(historyNumber);
                return user;
            }
        });
    }

    private Flowable<User> emptyUser() {
        User user = new User();
        user.setApproveNumber(0);
        user.setProgressNumber(0);
        user.setHistoryNumber(0);
        return Flowable.just(user);
    }
}
