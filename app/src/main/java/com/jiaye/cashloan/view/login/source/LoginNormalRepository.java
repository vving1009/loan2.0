package com.jiaye.cashloan.view.login.source;

import com.jiaye.cashloan.BuildConfig;
import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.data.login.Login;
import com.jiaye.cashloan.http.data.login.LoginNormalRequest;
import com.jiaye.cashloan.http.utils.SatcatcheResponseTransformer;
import com.satcatche.library.utils.RSAUtil;

import io.reactivex.Flowable;

/**
 * LoginNormalRepository
 *
 * @author 贾博瑄
 */
public class LoginNormalRepository implements LoginNormalDataSource {

    @Override
    public Flowable<Login> requestLogin(String phone, String password) {
        LoginNormalRequest request = new LoginNormalRequest();
        request.setPhone(phone);
        request.setPassword(RSAUtil.encryptByPublicKeyToBase64(password, BuildConfig.PUBLIC_KEY));
        return Flowable.just(request)
                .compose(new SatcatcheResponseTransformer<LoginNormalRequest, Login>("loginNormal"))
                .map(login -> {
                    LoanApplication.getInstance().getDbHelper().insertUser(phone, login.getToken(),
                            login.getId(), login.getName());
                    LoanApplication.getInstance().setupBuglyUserId(phone);
                    return login;
                });
    }
}
