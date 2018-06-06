package com.jiaye.cashloan.view.login.source;

import android.content.ContentValues;

import com.jiaye.cashloan.BuildConfig;
import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.data.auth.VerificationCode;
import com.jiaye.cashloan.http.data.auth.VerificationCodeRequest;
import com.jiaye.cashloan.http.data.auth.login.Login;
import com.jiaye.cashloan.http.data.auth.login.LoginRequest;
import com.jiaye.cashloan.http.utils.SatcatcheResponseTransformer;
import com.jiaye.cashloan.utils.RSAUtil;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * LoginRepository
 *
 * @author 贾博瑄
 */

public class LoginRepository implements LoginDataSource {

    @Override
    public Flowable<Login> requestLogin(final String phone, String password) {
        LoginRequest request = new LoginRequest();
        request.setPhone(phone);
        request.setPassword(RSAUtil.encryptByPublicKeyToBase64(password, BuildConfig.PUBLIC_KEY));
        return Flowable.just(request)
                .compose(new SatcatcheResponseTransformer<LoginRequest, Login>
                        ("login"))
                .map(new Function<Login, Login>() {
                    @Override
                    public Login apply(Login login) throws Exception {
                        ContentValues values = new ContentValues();
                        values.put("token", login.getToken());
                        values.put("phone", phone);
                        LoanApplication.getInstance().getSQLiteDatabase().insert("user", null, values);
                        LoanApplication.getInstance().setupBuglyUserId(phone);
                        return login;
                    }
                });
    }

    @Override
    public Flowable<VerificationCode> requestVerificationCode(String phone) {
        VerificationCodeRequest request = new VerificationCodeRequest();
        request.setPhone(phone);
        request.setStatus("0");
        return Flowable.just(request)
                .compose(new SatcatcheResponseTransformer<VerificationCodeRequest, VerificationCode>
                        ("verificationCode"));
    }
}
