package com.jiaye.loan.cashloan.view.view.auth.register;

import android.text.TextUtils;

import com.jiaye.loan.cashloan.R;
import com.jiaye.loan.cashloan.http.data.auth.register.Register;
import com.jiaye.loan.cashloan.http.data.auth.register.RegisterRequest;
import com.jiaye.loan.cashloan.http.data.auth.register.RegisterVerificationCode;
import com.jiaye.loan.cashloan.http.data.auth.register.RegisterVerificationCodeRequest;
import com.jiaye.loan.cashloan.http.utils.NetworkTransformer;
import com.jiaye.loan.cashloan.view.BasePresenterImpl;
import com.jiaye.loan.cashloan.view.ThrowableConsumer;
import com.jiaye.loan.cashloan.view.data.auth.register.source.RegisterDataSource;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * RegisterPresenter
 *
 * @author 贾博瑄
 */

public class RegisterPresenter extends BasePresenterImpl implements RegisterContract.Presenter {

    private final RegisterContract.View mView;

    private final RegisterDataSource mDataSource;

    public RegisterPresenter(RegisterContract.View view, RegisterDataSource dataSource) {
        super(dataSource);
        mView = view;
        mView.setPresenter(this);
        mDataSource = dataSource;
    }

    @Override
    public void verificationCode(RegisterVerificationCodeRequest request) {
        if (TextUtils.isEmpty(request.getPhone()) || request.getPhone().length() != 11) {/*检测手机号*/
            mView.showToastById(R.string.error_auth_phone);
        } else if (TextUtils.isEmpty(mView.getInputImgVerificationCode())
                || !mView.getInputImgVerificationCode().equals(mView.getImgVerificationCode())) {/*校验图形验证码*/
            mView.showToastById(R.string.error_auth_img_verification);
        } else {
            Observable.just(request)
                    .compose(new NetworkTransformer<RegisterVerificationCodeRequest, RegisterVerificationCode>(mView, "requestRegister"))
                    .subscribe(new Consumer<RegisterVerificationCode>() {
                        @Override
                        public void accept(RegisterVerificationCode registerVerificationCode) throws Exception {
                            mView.smsVerificationCodeCountDown();
                        }
                    }, new ThrowableConsumer(mView));
        }
    }

    @Override
    public void register(RegisterRequest request) {
        if (TextUtils.isEmpty(request.getPhone()) || request.getPhone().length() != 11) {/*检测手机号*/
            mView.showToastById(R.string.error_auth_phone);
        } else if (TextUtils.isEmpty(mView.getInputImgVerificationCode())
                || !mView.getInputImgVerificationCode().equals(mView.getImgVerificationCode())) {/*校验图形验证码*/
            mView.showToastById(R.string.error_auth_img_verification);
        } else if (TextUtils.isEmpty(request.getPassword()) || !request.getPassword().matches("^[a-zA-Z0-9]{6,12}$")) {/*检测密码规则*/
            mView.showToastById(R.string.error_auth_password);
        } else if (TextUtils.isEmpty(request.getSmsVerificationCode())) {/*检测是否填写验证码*/
            mView.showToastById(R.string.error_auth_sms_verification);
        } else if (!mView.isAgree()) {/*检测是否同意注册协议*/
            mView.showToastById(R.string.error_auth_agree);
        } else {
            Observable.just(request)
                    .compose(new NetworkTransformer<RegisterRequest, Register>(mView, "requestRegister"))
                    .observeOn(Schedulers.io())
                    .map(new Function<Register, Register>() {
                        @Override
                        public Register apply(Register register) throws Exception {
                            mDataSource.addUser(register);
                            return register;
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Register>() {
                        @Override
                        public void accept(Register register) throws Exception {
                            mView.finish();
                        }
                    }, new ThrowableConsumer(mView));
        }
    }
}
