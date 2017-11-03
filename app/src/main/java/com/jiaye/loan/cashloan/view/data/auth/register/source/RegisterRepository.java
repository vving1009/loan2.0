package com.jiaye.loan.cashloan.view.data.auth.register.source;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.jiaye.loan.cashloan.LoanApplication;
import com.jiaye.loan.cashloan.http.data.auth.register.Register;
import com.jiaye.loan.cashloan.persistence.DbHelper;

/**
 * RegisterRepository
 *
 * @author 贾博瑄
 */

public class RegisterRepository implements RegisterDataSource {

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
    public void addUser(Register register) {
        ContentValues values = new ContentValues();
        values.put("token", register.getToken());
        mDatabase.insert("user", null, values);
    }
}
