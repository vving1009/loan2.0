package com.jiaye.cashloan.view.data.my.source;

import android.database.Cursor;
import android.text.TextUtils;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.R;
import com.jiaye.cashloan.persistence.DbContract;
import com.jiaye.cashloan.view.LocalException;
import com.jiaye.cashloan.view.data.auth.User;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * MyRepository
 *
 * @author 贾博瑄
 */

public class MyRepository implements MyDataSource {

    @Override
    public Observable<User> requestUser() {
        // TODO: 2017/10/30 请求服务器返回数据,暂时采用本地模拟数据.
        User user = new User();
        user.setName("贾博瑄");
        user.setPhone("13752126558");
        user.setApproveNumber("0");
        user.setLoanNumber("0");
        user.setHistoryNumber("0");
        if (TextUtils.isEmpty(user.getName()) && TextUtils.isEmpty(user.getPhone())) {/*姓名和手机号码均为空*/
            user.setShowName("游客");
        } else if (TextUtils.isEmpty(user.getName())) {/*姓名为空手机号不为空*/
            String phone = user.getPhone();
            String start = phone.substring(0, 3);
            String end = phone.substring(7, 11);
            user.setShowName(start + "****" + end);
        } else {/*姓名不为空*/
            String name = user.getName();
            int l = name.length();
            if (l == 1) {
                user.setShowName(name);
            } else {
                StringBuilder s = new StringBuilder();
                for (int i = 0; i < l - 1; i++) {
                    s.append("*");
                }
                user.setShowName(s + name.substring(s.length()));
            }
        }
        return Observable.just(user);
    }

    @Override
    public Observable<User> queryUser() {
        String sql = "SELECT * FROM user;";
        return Observable.just(sql).map(new Function<String, User>() {
            @Override
            public User apply(String sql) throws Exception {
                String name = "";
                String phone = "";
                Cursor cursor = LoanApplication.getInstance().getSQLiteDatabase().rawQuery(sql, null);
                if (cursor != null) {
                    if (cursor.moveToNext()) {
                        name = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_NAME));
                        phone = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_PHONE));
                    }
                    cursor.close();
                }
                if (TextUtils.isEmpty(name) && TextUtils.isEmpty(phone)) {
                    throw new LocalException(R.string.error_auth_not_log_in);
                }
                User user = new User();
                user.setName(name);
                user.setPhone(phone);
                return user;
            }
        });
    }
}
