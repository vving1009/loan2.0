package com.jiaye.cashloan.view.data.auth.register.source;

import android.content.ContentValues;

import com.jiaye.cashloan.BuildConfig;
import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.data.auth.VerificationCode;
import com.jiaye.cashloan.http.data.auth.VerificationCodeRequest;
import com.jiaye.cashloan.http.data.auth.register.Register;
import com.jiaye.cashloan.http.data.auth.register.RegisterRequest;
import com.jiaye.cashloan.http.utils.SatcatcheResponseTransformer;
import com.jiaye.cashloan.utils.RSAUtil;

import io.reactivex.Flowable;

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
    public Flowable<Register> requestRegister(String phone, String password, String sms, String code) {
        RegisterRequest request = new RegisterRequest();
        request.setPhone(phone);
        request.setPassword(RSAUtil.encryptByPublicKeyToBase64(password, BuildConfig.PUBLIC_KEY));
        request.setSmsVerificationCode(sms);
        request.setReferralCode(code);
        return Flowable.just(request)
                .compose(new SatcatcheResponseTransformer<RegisterRequest, Register>
                        ("register"));
    }

    @Override
    public void addUser(Register register) {
        ContentValues values = new ContentValues();
        values.put("token", register.getToken());
        values.put("phone", register.getPhone());
        LoanApplication.getInstance().getSQLiteDatabase().insert("user", null, values);
    }
}
