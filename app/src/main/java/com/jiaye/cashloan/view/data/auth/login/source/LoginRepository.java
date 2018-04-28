package com.jiaye.cashloan.view.data.auth.login.source;

import android.content.ContentValues;

import com.jiaye.cashloan.BuildConfig;
import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.data.auth.login.Login;
import com.jiaye.cashloan.http.data.auth.login.LoginRequest;
import com.jiaye.cashloan.http.utils.SatcatcheResponseTransformer;
import com.jiaye.cashloan.utils.RSAUtil;

import io.reactivex.Flowable;

/**
 * LoginRepository
 *
 * @author 贾博瑄
 */

public class LoginRepository implements LoginDataSource {

    @Override
    public Flowable<Login> requestLogin(String phone, String password) {
        LoginRequest request = new LoginRequest();
        request.setPhone(phone);
        request.setPassword(RSAUtil.encryptByPublicKeyToBase64(password, BuildConfig.PUBLIC_KEY));
        return Flowable.just(request)
                .compose(new SatcatcheResponseTransformer<LoginRequest, Login>
                        ("login"));
    }

    @Override
    public void addUser(Login login) {
        ContentValues values = new ContentValues();
        values.put("token", login.getToken());
        values.put("phone", login.getPhone());
        LoanApplication.getInstance().getSQLiteDatabase().insert("user", null, values);
        LoanApplication.getInstance().setupBuglyUserId(login.getPhone());
    }
}
