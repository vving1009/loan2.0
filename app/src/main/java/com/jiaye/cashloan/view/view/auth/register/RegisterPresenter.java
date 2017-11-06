package com.jiaye.cashloan.view.view.auth.register;

import android.text.TextUtils;

import com.jiaye.cashloan.BuildConfig;
import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.auth.VerificationCode;
import com.jiaye.cashloan.http.data.auth.VerificationCodeRequest;
import com.jiaye.cashloan.http.data.auth.register.Register;
import com.jiaye.cashloan.http.data.auth.register.RegisterRequest;
import com.jiaye.cashloan.http.utils.ResponseTransformer;
import com.jiaye.cashloan.utils.RSAUtil;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.data.auth.register.source.RegisterDataSource;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
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
        mView = view;
        mView.setPresenter(this);
        mDataSource = dataSource;
    }

    @Override
    public void verificationCode() {
        if (TextUtils.isEmpty(mView.getPhone()) || mView.getPhone().length() != 11) {/*检测手机号*/
            mView.showToastById(R.string.error_auth_phone);
        } else if (TextUtils.isEmpty(mView.getInputImgVerificationCode())
                || !mView.getInputImgVerificationCode().equals(mView.getImgVerificationCode())) {/*校验图形验证码*/
            mView.showToastById(R.string.error_auth_img_verification);
        } else {
            VerificationCodeRequest request = new VerificationCodeRequest();
            request.setPhone(mView.getPhone());
            request.setStatus("0");
            Flowable.just(request)
                    .compose(new ResponseTransformer<VerificationCodeRequest, VerificationCode>("verificationCode"))
                    .compose(new ViewTransformer<VerificationCode>(mView))
                    .subscribe(new Consumer<VerificationCode>() {
                        @Override
                        public void accept(VerificationCode verificationCode) throws Exception {
                            mView.smsVerificationCodeCountDown();
                        }
                    }, new ThrowableConsumer(mView));
        }
    }

    @Override
    public void register() {
        if (TextUtils.isEmpty(mView.getPhone()) || mView.getPhone().length() != 11) {/*检测手机号*/
            mView.showToastById(R.string.error_auth_phone);
        } else if (TextUtils.isEmpty(mView.getInputImgVerificationCode())
                || !mView.getInputImgVerificationCode().equals(mView.getImgVerificationCode())) {/*校验图形验证码*/
            mView.showToastById(R.string.error_auth_img_verification);
        } else if (TextUtils.isEmpty(mView.getPassword()) || !mView.getPassword().matches("^[a-zA-Z0-9]{6,12}$")) {/*检测密码规则*/
            mView.showToastById(R.string.error_auth_password);
        } else if (TextUtils.isEmpty(mView.getInputSmsVerificationCode())) {/*检测是否填写验证码*/
            mView.showToastById(R.string.error_auth_sms_verification);
        } else if (!TextUtils.isEmpty(mView.getReferralCode()) && mView.getPhone().equals(mView.getReferralCode())) {/*检测推荐人手机号*/
            mView.showToastById(R.string.error_auth_referral);
        } else if (!mView.isAgree()) {/*检测是否同意注册协议*/
            mView.showToastById(R.string.error_auth_agree);
        } else {
            RegisterRequest request = new RegisterRequest();
            request.setPhone(mView.getPhone());
            request.setImgVerificationCode(mView.getInputImgVerificationCode());
            request.setPassword(RSAUtil.encryptByPublicKeyToBase64(mView.getPassword(), BuildConfig.PUBLIC_KEY));
            request.setSmsVerificationCode(mView.getInputSmsVerificationCode());
            request.setReferralCode(mView.getReferralCode());
            Disposable disposable = Flowable.just(request)
                    .compose(new ResponseTransformer<RegisterRequest, Register>("register"))
                    .compose(new ViewTransformer<Register>(mView))
                    .map(new Function<Register, Register>() {
                        @Override
                        public Register apply(Register register) throws Exception {
                            register.setPhone(mView.getPhone());
                            return register;
                        }
                    })
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
            mCompositeDisposable.add(disposable);
        }
    }
}
