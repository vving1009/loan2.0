package com.jiaye.cashloan.view.view.auth.register;

import android.text.TextUtils;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.auth.VerificationCode;
import com.jiaye.cashloan.http.data.auth.register.Register;
import com.jiaye.cashloan.utils.RegexUtil;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.data.auth.register.source.RegisterDataSource;

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
        mDataSource = dataSource;
    }

    @Override
    public void checkInput() {
        if (TextUtils.isEmpty(mView.getPhone())) {/*检测手机号*/
            mView.setEnable(false);
        } else if (TextUtils.isEmpty(mView.getInputImgVerificationCode())) {/*校验图形验证码*/
            mView.setEnable(false);
        } else if (TextUtils.isEmpty(mView.getPassword())) {/*检测密码规则*/
            mView.setEnable(false);
        } else if (TextUtils.isEmpty(mView.getInputSmsVerificationCode())) {/*检测是否填写验证码*/
            mView.setEnable(false);
        } else {
            mView.setEnable(true);
        }
    }

    @Override
    public void verificationCode() {
        if (TextUtils.isEmpty(mView.getPhone()) || mView.getPhone().length() != 11 || !mView.getPhone().matches(RegexUtil.phone())) {/*检测手机号*/
            mView.showToastById(R.string.error_auth_phone);
        } else if (TextUtils.isEmpty(mView.getInputImgVerificationCode())
                || !mView.getInputImgVerificationCode().equals(mView.getImgVerificationCode())) {/*校验图形验证码*/
            mView.showToastById(R.string.error_auth_img_verification);
        } else {
            Disposable disposable = mDataSource.requestVerificationCode(mView.getPhone())
                    .compose(new ViewTransformer<VerificationCode>() {
                        @Override
                        public void accept() {
                            super.accept();
                            mView.showProgressDialog();
                        }
                    })
                    .subscribe(new Consumer<VerificationCode>() {
                        @Override
                        public void accept(VerificationCode verificationCode) throws Exception {
                            mView.smsVerificationCodeCountDown();
                            mView.dismissProgressDialog();
                        }
                    }, new ThrowableConsumer(mView));
            mCompositeDisposable.add(disposable);
        }
    }

    @Override
    public void register() {
        if (TextUtils.isEmpty(mView.getPhone()) || mView.getPhone().length() != 11 || !mView.getPhone().matches(RegexUtil.phone())) {/*检测手机号*/
            mView.showToastById(R.string.error_auth_phone);
        } else if (TextUtils.isEmpty(mView.getInputImgVerificationCode())
                || !mView.getInputImgVerificationCode().equals(mView.getImgVerificationCode())) {/*校验图形验证码*/
            mView.showToastById(R.string.error_auth_img_verification);
        } else if (TextUtils.isEmpty(mView.getPassword()) || !mView.getPassword().matches(RegexUtil.password())) {/*检测密码规则*/
            mView.showToastById(R.string.error_auth_password);
        } else if (TextUtils.isEmpty(mView.getInputSmsVerificationCode())) {/*检测是否填写验证码*/
            mView.showToastById(R.string.error_auth_sms_verification);
        } else if (!TextUtils.isEmpty(mView.getReferralCode()) && (mView.getPhone().equals(mView.getReferralCode()) || !mView.getReferralCode().matches(RegexUtil.phone()))) {/*检测推荐人手机号*/
            mView.showToastById(R.string.error_auth_referral);
        } else if (!mView.isAgree()) {/*检测是否同意注册协议*/
            mView.showToastById(R.string.error_auth_agree);
        } else {
            Disposable disposable = mDataSource.requestRegister(mView.getPhone(),
                    mView.getPassword(),
                    mView.getInputSmsVerificationCode(),
                    mView.getReferralCode())
                    .compose(new ViewTransformer<Register>() {
                        @Override
                        public void accept() {
                            mView.showProgressDialog();
                        }
                    })
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
                            mView.dismissProgressDialog();
                            mView.finish();
                        }
                    }, new ThrowableConsumer(mView));
            mCompositeDisposable.add(disposable);
        }
    }
}
