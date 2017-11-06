package com.jiaye.cashloan.view.data.auth.login.source;

import android.content.ContentValues;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.data.auth.login.Login;

/**
 * LoginRepository
 *
 * @author 贾博瑄
 */

public class LoginRepository implements LoginDataSource {

    @Override
    public void addUser(Login login) {
        ContentValues values = new ContentValues();
        values.put("token", login.getToken());
        values.put("phone", login.getPhone());
        LoanApplication.getInstance().getSQLiteDatabase().insert("user", null, values);
    }
}
