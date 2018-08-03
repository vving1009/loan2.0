package com.jiaye.cashloan.view.register.source;

import com.jiaye.cashloan.BuildConfig;
import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.forgetpassword.ForgetPasswordCodeRequest;
import com.jiaye.cashloan.http.data.login.Login;
import com.jiaye.cashloan.http.register.RegisterRequest;
import com.jiaye.cashloan.http.utils.SatcatcheResponseTransformer;
import com.satcatche.library.utils.RSAUtil;

import io.reactivex.Flowable;

/**
 * RegisterRepository
 *
 * @author 贾博瑄
 */

public class RegisterRepository implements RegisterDataSource {

    @Override
    public Flowable<Login> requestRegister(String phone, String code, String password) {
        RegisterRequest request = new RegisterRequest();
        request.setPhone(phone);
        request.setCode(code);
        request.setPassword(RSAUtil.encryptByPublicKeyToBase64(password, BuildConfig.PUBLIC_KEY));
        return Flowable.just(request)
                .compose(new SatcatcheResponseTransformer<RegisterRequest, Login>("register"))
                .map(login -> {
                    LoanApplication.getInstance().getDbHelper().insertUser(phone, login.getToken(),
                            login.getId(), login.getName());
                    LoanApplication.getInstance().setupBuglyUserId(phone);
                    return login;
                });
    }

    @Override
    public Flowable<EmptyResponse> requestRegisterCode(String phone) {
        ForgetPasswordCodeRequest request = new ForgetPasswordCodeRequest();
        request.setPhone(phone);
        request.setStatus(0);
        return Flowable.just(request)
                .compose(new SatcatcheResponseTransformer<ForgetPasswordCodeRequest, EmptyResponse>
                        ("forgetPasswordCode"));
    }
}
