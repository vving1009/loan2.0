package com.jiaye.cashloan.view.data.market;

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

    @Override
    public Flowable<User> queryUser() {
        String sql = "SELECT * FROM user;";
        return Flowable.just(sql).map(new Function<String, User>() {
            @Override
            public User apply(String sql) throws Exception {
                String phone = "";
                String approveNumber = "";
                String progressNumber = "";
                String historyNumber = "";
                String loanApproveId = "";
                String loanProgressId = "";
                String name = "";
                Cursor cursor = LoanApplication.getInstance().getSQLiteDatabase().rawQuery(sql, null);
                if (cursor != null) {
                    if (cursor.moveToNext()) {
                        phone = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_PHONE));
                        approveNumber = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_APPROVE_NUMBER));
                        progressNumber = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_PROGRESS_NUMBER));
                        historyNumber = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_HISTORY_NUMBER));
                        loanApproveId = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_LOAN_APPROVE_ID));
                        loanProgressId = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_LOAN_PROGRESS_ID));
                        name = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_OCR_NAME));
                    }
                    cursor.close();
                }
                User user = new User();
                user.setName(name);
                user.setPhone(phone);
                user.setApproveNumber(approveNumber);
                user.setProgressNumber(progressNumber);
                user.setHistoryNumber(historyNumber);
                user.setLoanApproveId(loanApproveId);
                user.setLoanProgressId(loanProgressId);
                return user;
            }
        });
    }

}
