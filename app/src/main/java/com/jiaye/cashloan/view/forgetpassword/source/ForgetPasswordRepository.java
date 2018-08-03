package com.jiaye.cashloan.view.forgetpassword.source;

import com.jiaye.cashloan.BuildConfig;
import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.forgetpassword.ForgetPasswordCodeRequest;
import com.jiaye.cashloan.http.data.forgetpassword.ForgetPasswordRequest;
import com.jiaye.cashloan.http.utils.SatcatcheResponseTransformer;
import com.satcatche.library.utils.RSAUtil;

import io.reactivex.Flowable;

/**
 * ForgetPasswordRepository
 *
 * @author 贾博瑄
 */

public class ForgetPasswordRepository implements ForgetPasswordDataSource {

    @Override
    public Flowable<EmptyResponse> requestForgetPassword(String phone, String code, String password) {
        ForgetPasswordRequest request = new ForgetPasswordRequest();
        request.setPhone(phone);
        request.setCode(code);
        request.setPassword(RSAUtil.encryptByPublicKeyToBase64(password, BuildConfig.PUBLIC_KEY));
        return Flowable.just(request)
                .compose(new SatcatcheResponseTransformer<ForgetPasswordRequest, EmptyResponse>("forgetPassword"));
    }

    @Override
    public Flowable<EmptyResponse> requestForgetPasswordCode(String phone) {
        ForgetPasswordCodeRequest request = new ForgetPasswordCodeRequest();
        request.setPhone(phone);
        request.setStatus(1);
        return Flowable.just(request)
                .compose(new SatcatcheResponseTransformer<ForgetPasswordCodeRequest, EmptyResponse>
                        ("forgetPasswordCode"));
    }
}
