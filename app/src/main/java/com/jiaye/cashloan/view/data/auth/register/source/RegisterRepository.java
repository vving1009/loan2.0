package com.jiaye.cashloan.view.data.auth.register.source;

import android.content.ContentValues;

import com.jiaye.cashloan.BuildConfig;
import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.data.auth.VerificationCode;
import com.jiaye.cashloan.http.data.auth.VerificationCodeRequest;
import com.jiaye.cashloan.http.data.auth.login.Login;
import com.jiaye.cashloan.http.data.auth.login.LoginRequest;
import com.jiaye.cashloan.http.data.auth.register.Register;
import com.jiaye.cashloan.http.data.auth.register.RegisterRequest;
import com.jiaye.cashloan.http.utils.SatcatcheResponseTransformer;
import com.jiaye.cashloan.utils.RSAUtil;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * RegisterRepository
 *
 * @author 贾博瑄
 */

public class RegisterRepository implements RegisterDataSource {

    @Override
    public Flowable<VerificationCode> requestVerificationCode(String phone) {
        VerificationCodeRequest request = new VerificationCodeRequest();
        request.setPhone(phone);
        request.setStatus("0");
        return Flowable.just(request)
                .compose(new SatcatcheResponseTransformer<VerificationCodeRequest, VerificationCode>
                        ("verificationCode"));
    }

    @Override
    public Flowable<Login> requestRegister(final String phone, final String password, String sms, String code) {
        RegisterRequest request = new RegisterRequest();
        request.setPhone(phone);
        request.setPassword(RSAUtil.encryptByPublicKeyToBase64(password, BuildConfig.PUBLIC_KEY));
        request.setSmsVerificationCode(sms);
        request.setReferralCode(code);
        return Flowable.just(request)
                .compose(new SatcatcheResponseTransformer<RegisterRequest, Register>
                        ("register"))
                .map(new Function<Register, LoginRequest>() {
                    @Override
                    public LoginRequest apply(Register register) throws Exception {
                        LoginRequest loginRequest = new LoginRequest();
                        loginRequest.setPhone(phone);
                        loginRequest.setPassword(RSAUtil.encryptByPublicKeyToBase64(password, BuildConfig.PUBLIC_KEY));
                        return loginRequest;
                    }
                })
                .compose(new SatcatcheResponseTransformer<LoginRequest, Login>("login"))
                .map(new Function<Login, Login>() {
                    @Override
                    public Login apply(Login login) throws Exception {
                        ContentValues values = new ContentValues();
                        values.put("token", login.getToken());
                        values.put("phone", phone);
                        LoanApplication.getInstance().getSQLiteDatabase().insert("user", null, values);
                        return login;
                    }
                });
    }
}
