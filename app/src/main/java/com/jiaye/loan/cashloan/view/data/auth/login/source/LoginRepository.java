package com.jiaye.loan.cashloan.view.data.auth.login.source;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.jiaye.loan.cashloan.LoanApplication;
import com.jiaye.loan.cashloan.http.data.auth.login.Login;
import com.jiaye.loan.cashloan.persistence.DbHelper;

/**
 * LoginRepository
 *
 * @author 贾博瑄
 */

public class LoginRepository implements LoginDataSource {

    private SQLiteDatabase mDatabase;

    @Override
    public void open() {
        DbHelper dbHelper = new DbHelper(LoanApplication.getInstance());
        mDatabase = dbHelper.getWritableDatabase();
    }

    @Override
    public void close() {
        mDatabase.close();
    }

    @Override
    public void addUser(Login login) {
        ContentValues values = new ContentValues();
        values.put("token", login.getToken());
        values.put("phone", login.getPhone());
        mDatabase.insert("user", null, values);
    }
}
