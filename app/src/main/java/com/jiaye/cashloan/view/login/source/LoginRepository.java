package com.jiaye.cashloan.view.login.source;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.login.Login;
import com.jiaye.cashloan.http.data.login.LoginRequest;
import com.jiaye.cashloan.http.data.login.VerificationCodeRequest;
import com.jiaye.cashloan.http.utils.SatcatcheResponseTransformer;

import io.reactivex.Flowable;

/**
 * LoginRepository
 *
 * @author 贾博瑄
 */

public class LoginRepository implements LoginDataSource {

    @Override
    public Flowable<Login> requestLogin(final String phone, String code) {
        LoginRequest request = new LoginRequest();
        request.setPhone(phone);
        request.setCode(code);
        return Flowable.just(request)
                .compose(new SatcatcheResponseTransformer<LoginRequest, Login>("login"))
                .map(login -> {
                    LoanApplication.getInstance().getDbHelper().insertUser(phone, login.getToken(),
                            login.getId(), login.getName());
                    LoanApplication.getInstance().setupBuglyUserId(phone);
                    return login;
                });
    }

    @Override
    public Flowable<EmptyResponse> requestVerificationCode(String phone) {
        VerificationCodeRequest request = new VerificationCodeRequest();
        request.setPhone(phone);
        return Flowable.just(request)
                .compose(new SatcatcheResponseTransformer<VerificationCodeRequest, EmptyResponse>
                        ("verificationCode"));
    }
}
