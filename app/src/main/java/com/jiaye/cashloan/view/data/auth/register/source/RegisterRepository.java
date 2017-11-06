package com.jiaye.cashloan.view.data.auth.register.source;

import android.content.ContentValues;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.data.auth.register.Register;

/**
 * RegisterRepository
 *
 * @author 贾博瑄
 */

public class RegisterRepository implements RegisterDataSource {

    @Override
    public void addUser(Register register) {
        ContentValues values = new ContentValues();
        values.put("token", register.getToken());
        values.put("phone", register.getPhone());
        LoanApplication.getInstance().getSQLiteDatabase().insert("user", null, values);
    }
}
